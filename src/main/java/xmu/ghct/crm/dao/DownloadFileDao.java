package xmu.ghct.crm.dao;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static jdk.nashorn.internal.runtime.GlobalFunctions.encodeURIComponent;


@Component
public class DownloadFileDao {


    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void downloadFile(HttpServletResponse response,String filePath) throws UnsupportedEncodingException {
        String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        String type=fileName.substring(fileName.lastIndexOf("."));
        File file=new File(filePath);
        if(file.exists()) {  //判断文件父目录是否存在
             response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");

            //设置文件头
            String headerValue = "attachment;";
            headerValue += " filename=\"" + encodeURIComponent(fileName) +"\";";
            headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);
            headerValue += encodeURIComponent(type);                             //添加文件后缀
            response.setHeader("Content-Disposition", headerValue);
            byte[] buff = new byte[1024];                                        //5.创建数据缓冲区
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = response.getOutputStream();                              //6.通过response对象获取OutputStream流
                bis = new BufferedInputStream(new FileInputStream(file));     //4.根据文件路径获取要下载的文件输入流
                int i = bis.read(buff);                                       //7.将FileInputStream流写入到buffer缓冲区
                while (i != -1) {
                    os.write(buff, 0, buff.length); //8.使用将OutputStream缓冲区的数据输出到客户端浏览器
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * @author hzm
     * 文件批量下载
     * @param response
     * @param paths
     * @throws UnsupportedEncodingException
     */
    public void multiFileDownload(HttpServletResponse response,ArrayList<String> paths) throws UnsupportedEncodingException {

        //存放--服务器上zip文件的目录
        String directory = "H:\\学科\\J2EE\\ghct1.0\\批量文件";
        File directoryFile=new File(directory);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }
        //设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = formatter.format(new Date())+".zip";
        String strZipPath = directory+"\\"+zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try{
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i<paths.size() ;i++){

                //解码获取真实路径与文件名
                String realFilePath = URLDecoder.decode(paths.get(i),"UTF-8");
                String realFileName=realFilePath.substring(realFilePath.lastIndexOf("\\")+1);
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if(file.exists())
                {
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */

                    ZipEntry zipEntry = new ZipEntry(realFileName);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
                    {
                        zipStream.write(buf, 0, read);
                    }
                }

                else {
                    //throw new fileNotFineException();
                    System.out.println("未找到文件："+realFilePath);}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream){
                    zipStream.flush();
                    zipStream.close();
                }
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            downloadFile(response,strZipPath);
            zipFile.delete();
        }
    }
}