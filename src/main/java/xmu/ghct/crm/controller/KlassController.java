package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.service.KlassService;
import xmu.ghct.crm.service.UploadExcelService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author hzm
 */
@CrossOrigin
@RestController
public class KlassController {
    @Autowired
    KlassService klassService;

    @Autowired
    UploadExcelService uploadExcelService;

    @RequestMapping(value="/course/{courseId}/klass",method = RequestMethod.POST)
    @ResponseBody
    public boolean createKlass(@PathVariable("courseId")String courseId,@RequestBody Map<String,Object> klassMap) throws NotFoundException, SQLException {
        //创建之前先判断，是否有同一课程下的同样的班级次序，若有则失败
        int flag=klassService.creatKlass(new BigInteger(courseId),klassMap);
        if(flag>0) {
            return true;
        } else {
            return false;
        }
    }


    @RequestMapping(value="/course/{courseId}/klass",method = RequestMethod.GET)
    @ResponseBody
    public List<Klass> listKlassByCourseId(@PathVariable("courseId") Long courseId) throws NotFoundException {
        return klassService.listKlassByCourseId(BigInteger.valueOf(courseId));
    }

    /**
     * 删除某一课程下的所有班级
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/deleteKlass",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByCourseId(@PathVariable("courseId")String courseId) throws NotFoundException {
        int flag= klassService.deleteKlassByCourseId(new BigInteger(courseId));
        if(flag>0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除某个班级
     * @param klassId
     * @return
     */
    @RequestMapping(value="/course/klass/{klassId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByKlassId(@PathVariable("klassId") String klassId) throws NotFoundException {
        System.out.println("klassId "+klassId);
        return klassService.deleteKlassByKlassId(new BigInteger(klassId));
    }


    /**
     * 上传学生名单
     * @param klassId
     * @param file
     * @return
     */
    @PostMapping("/klass/{klassId}/uploadStudentNameList")
    public Boolean add(@PathVariable("klassId")String klassId,@RequestParam("file") MultipartFile file){
         return uploadExcelService.addStudentInfo(new BigInteger(klassId),file);
    }


}
