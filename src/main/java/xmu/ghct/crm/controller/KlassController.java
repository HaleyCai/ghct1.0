package xmu.ghct.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.service.KlassService;
import xmu.ghct.crm.service.UploadExcelService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
public class KlassController {
    @Autowired
    KlassService klassService;

    @Autowired
    UploadExcelService uploadExcelService;

    @RequestMapping(value="/course/{courseId}/klass",method = RequestMethod.POST)
    @ResponseBody
    public boolean createKlass(@PathVariable("courseId")Long courseId,@RequestBody Map<String,Object> klassMap)  {
        //创建之前先判断，是否有同一课程下的同样的班级次序，若有则失败
        int flag=klassService.creatKlass(BigInteger.valueOf(courseId),klassMap);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/klass",method = RequestMethod.GET)
    @ResponseBody
    public List<Klass> listKlassByCourseId(@PathVariable("courseId") Long courseId){
        return klassService.listKlassByCourseId(BigInteger.valueOf(courseId));
    }

    /**
     * 删除某一课程下的所有班级
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/deleteKlass",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByCourseId(@PathVariable("courseId")Long courseId){
        int flag= klassService.deleteKlassByCourseId(BigInteger.valueOf(courseId));
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/class/{klassId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByKlassId(@PathVariable("klassId") Long klassId){
        return klassService.deleteKlassByKlassId(BigInteger.valueOf(klassId));
    }


    /**
     * 上传学生名单
     * @param klassId
     * @param file
     * @return
     */
    @PostMapping("klass/{klassId}/uploadStudentNameList")
    public Boolean add(@PathVariable("klassId")BigInteger klassId,@RequestParam("file") MultipartFile file){
        //Map<Integer, Map<Integer,Object>> map = uploadExcelService.addCustomerInfo(file);
         return uploadExcelService.addCustomerInfo(klassId,file);
    }


}
