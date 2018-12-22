package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.service.ScoreService;
import xmu.ghct.crm.service.SeminarService;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class SeminarController {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    ScoreService scoreService;
    /**
     * 根据轮次id获取该轮次下所有的讨论课的简略信息
     * @param roundId
     * @return
     */
    @RequestMapping(value="round/{roundId}/seminar",method = RequestMethod.GET)
    @ResponseBody
    public List<SeminarSimpleVO> getSeminarByRoundId(@PathVariable("roundId") BigInteger roundId)
    {
        return seminarService.getSeminarByRoundId(roundId);
    }

    @RequestMapping(value="/seminar",method = RequestMethod.POST)
    public Boolean creatSeminar(@RequestBody Map<String,Object> seminarMap) throws ParseException {
        int flag=seminarService.creatSeminar(seminarMap);
        if(flag>0) return true;
        else  return false;
    }

    @RequestMapping(value="/seminar/{seminarId}/class",method = RequestMethod.GET)
    public List<Klass> listKlassBySeminarId(@PathVariable("seminarId") BigInteger seminarId){
        return seminarService.listKlassBySeminarId(seminarId);
    }

    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.PUT)
    public boolean updateSeminarBySeminarId(@PathVariable("seminarId")BigInteger seminarId,@RequestBody Map<String,Object> seminarMap)throws ParseException{
        int flag=seminarService.updateSeminarBySeminarId(seminarId,seminarMap);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.DELETE)
    public boolean deleteSeminarBySeminarId(@PathVariable("seminarId") BigInteger seminarId){
        int flag_1=seminarService.deleteSeminarBySeminarId(seminarId);
        int flag_2=seminarService.deleteKlassSeminarBySeminarId(seminarId);
        int flag_3=scoreService.deleteSeminarScoreBySeminarId(seminarId);
        if(flag_1>0&&flag_2>0&&flag_3>0) return true;
        else return false;
    }

    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.GET)
    public  Seminar getSeminarBySeminarId(@PathVariable("seminarId") BigInteger seminarId){
        return seminarService.getSeminarBySeminarId(seminarId);
    }

    @PostMapping("/seminar/{seminarId}/klass/{klassId}")
    public boolean updateKlassSeminarBySeminarIdAndKlassId(@PathVariable("klassId") BigInteger klassId,@PathVariable("seminarId") BigInteger seminarId){
        int flag=seminarService.updateKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
        if(flag>0) return true;
        else return false;
    }

}
