package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.SeminarMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static jdk.nashorn.internal.runtime.GlobalFunctions.encodeURIComponent;

/**
 * @author hzm
 */
@Component
public class DownloadFileDao {

    @Autowired
    SeminarMapper seminarMapper;


    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void downloadFile(HttpServletResponse response, HttpServletRequest request,String filePath) throws UnsupportedEncodingException {

        String fileName;
        if(filePath.contains("\\")){
            fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        }
        else{
            fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        }
        String type=fileName.substring(fileName.lastIndexOf("."));
        File file=new File(filePath);

        final String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        System.out.println(userAgent);
        if(file.exists()) {
            //判断文件父目录是否存在
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");

            //设置文件头
            String headerValue = "attachment; filename=\"";
            if(userAgent.contains("msie")){
                System.out.println("msie");
                headerValue += URLEncoder.encode(fileName,"UTF8")+URLEncoder.encode(type,"UTF8");
            }
            else if(userAgent.contains("safari")||userAgent.contains("postman")){
                System.out.println("safari");
                headerValue +=encodeURIComponent(fileName) +"\";";
                headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);
                headerValue += encodeURIComponent(type);
                //添加文件后缀
            }  else if(userAgent.contains("mozilla")){
                System.out.println("mozilla");
                headerValue +=new String(fileName.getBytes(), "ISO8859-1")+new String(type.getBytes(), "ISO8859-1");
            } else {
                System.out.println("other");
                //其他浏览器
                headerValue += URLEncoder.encode(fileName, "UTF8") + URLEncoder.encode(type, "UTF8");
            }
            response.setHeader("Content-Disposition", headerValue);
            //5.创建数据缓冲区
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                //6.通过response对象获取OutputStream流
                os = response.getOutputStream();
                //4.根据文件路径获取要下载的文件输入流
                bis = new BufferedInputStream(new FileInputStream(file));
                //7.将FileInputStream流写入到buffer缓冲区
                int i = bis.read(buff);
                while (i != -1) {
                    //8.使用将OutputStream缓冲区的数据输出到客户端浏览器
                    os.write(buff, 0, buff.length);
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
    public void multiFileDownload(BigInteger seminarId,HttpServletResponse response,HttpServletRequest request, List<String> paths) throws UnsupportedEncodingException {

        Seminar seminar=seminarMapper.getSeminarBySeminarId(seminarId);

        //存放--服务器上zip文件的目录
        File directoryFile=new File("temp");
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }
        //设置最终输出zip文件的目录+文件名
        String zipFileName = seminar.getSeminarName()+".zip";
        String strZipPath = directoryFile+"\\"+zipFileName;

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
                System.out.println(realFilePath);
                String realFileName=realFilePath.substring(realFilePath.lastIndexOf("\\")+1);
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if(file.exists())
                {
                    //将需要压缩的文件格式化为输入流
                    zipSource = new FileInputStream(file);
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */
                    //在压缩目录中文件的名字
                    ZipEntry zipEntry = new ZipEntry(realFileName);
                    //定位该压缩条目位置，开始写入文件到压缩包中
                    zipStream.putNextEntry(zipEntry);
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
                if(null != bufferStream) {
                    bufferStream.close();
                }
                if(null != zipStream){
                    zipStream.flush();
                    zipStream.close();
                }
                if(null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            downloadFile(response,request,strZipPath);
            zipFile.delete();
            directoryFile.delete();
        }
    }
}
