package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;
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
        int flag=klassService.creatKlass(courseId,klassMap);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/class",method = RequestMethod.GET)
    @ResponseBody
    public List<Klass> listKlassByCourseId(@PathVariable("courseId") BigInteger courseId){
        return klassService.listKlassByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/class/{klassId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByCourseIdAndKlassId(@PathVariable("courseId")BigInteger courseId,@PathVariable("klassId")BigInteger klassId){
        int flag= klassService.deleteKlassByCourseIdAndKlassId(courseId, klassId);
        if(flag>0) return true;
        else return false;
    }
}
