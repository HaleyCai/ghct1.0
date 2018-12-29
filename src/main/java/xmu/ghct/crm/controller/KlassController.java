package xmu.ghct.crm.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.service.KlassService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
public class KlassController {
    @Autowired
    KlassService klassService;

    @RequestMapping(value="/course/{courseId}/class",method = RequestMethod.POST)
    @ResponseBody
    public boolean createKlass(@PathVariable("courseId")BigInteger courseId,@RequestBody Map<String,Object> klassMap)  {
        //创建之前先判断，是否有同一课程下的同样的班级次序，若有则失败
        int flag=klassService.creatKlass(courseId,klassMap);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/class",method = RequestMethod.GET)
    @ResponseBody
    public List<Klass> listKlassByCourseId(@PathVariable("courseId") BigInteger courseId){
        return klassService.listKlassByCourseId(courseId);
    }

    /**
     * 删除某一课程下的所有班级
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/deleteKlass",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByCourseId(@PathVariable("courseId")BigInteger courseId){
        int flag= klassService.deleteKlassByCourseId(courseId);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/class/{klassId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByKlassId(@PathVariable("klassId") BigInteger klassId){
        return klassService.deleteKlassByKlassId(klassId);
    }


}
