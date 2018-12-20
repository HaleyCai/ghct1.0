package xmu.ghct.crm.controller;

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
    @RequestMapping(value="/course/{courseId}/class/deleteKlass",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteKlassByCourseIdAndKlassId(@PathVariable("courseId")BigInteger courseId){
        int flag= klassService.deleteKlassByCourseIdAndKlassId(courseId);
        if(flag>0) return true;
        else return false;
    }

    /**
     * 根据roundId获取轮次信息
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.GET)
    public RoundVO getRoundByRoundId(@PathVariable("roundId") BigInteger roundId)
    {
        return klassService.getRoundByRoundId(roundId);
    }

    /**
     * 根据roundId修改轮次信息
     * @param roundId
     * @return
     */
    @RequestMapping(value = "/round/{roundId}",method = RequestMethod.PUT)
    public boolean modifyRoundByRoundId(@PathVariable("roundId") BigInteger roundId,@RequestBody Map<String,Object> inMap)
    {
        RoundVO roundVO=new RoundVO();
        roundVO.setRoundId((BigInteger) inMap.get("roundId"));
        roundVO.setPresentationScoreMethod(inMap.get("presentationScoreMethod").toString());
        roundVO.setReportScoreMethod(inMap.get("reportScoreMethod").toString());
        roundVO.setQuestionScoreMethod(inMap.get("questionScoreMethod").toString());
        return klassService.modifyRoundByRoundId(roundVO);
    }
}
