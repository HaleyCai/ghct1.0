package xmu.ghct.crm.dao;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

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
}
