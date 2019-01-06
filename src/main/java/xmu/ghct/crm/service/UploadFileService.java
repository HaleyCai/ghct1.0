package xmu.ghct.crm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hzm
 */
@Service
public class UploadFileService {

    public Map<String,String> uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if(fileName.contains("\\")){
            fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
        }
        File fileDir = new File("tmp");
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        String path = fileDir.getAbsolutePath();
        System.out.println(path);
        file.transferTo(new File(fileDir.getAbsolutePath(),fileName));
        String filePath=fileDir.getAbsolutePath()+"\\"+fileName;
        System.out.println(filePath);
        Map<String,String> uploadMap=new HashMap<>(16);
        uploadMap.put("name",fileName);
        uploadMap.put("path",filePath);
        return uploadMap;
    }

}
