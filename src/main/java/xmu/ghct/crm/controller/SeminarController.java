package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.SeminarScoreVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.service.KlassService;
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

    @Autowired
    KlassService klassService;
    /**
     * 根据轮次id获取该轮次下所有的讨论课的简略信息
     * @param roundId
     * @return
     */
    @RequestMapping(value="/round/{roundId}/seminar",method = RequestMethod.GET)
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

    @RequestMapping(value="/seminar/{seminarId}/klass",method = RequestMethod.GET)
    public List<Klass> listKlassBySeminarId(@PathVariable("seminarId") BigInteger seminarId){
        return klassService.listKlassBySeminarId(seminarId);
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

    @PutMapping("/seminar/{seminarId}/klass/{klassId}")
    public boolean updateKlassSeminarBySeminarIdAndKlassId(@PathVariable("klassId") BigInteger klassId,@PathVariable("seminarId") BigInteger seminarId,
                                                           @RequestBody Map<String,Object> klassMap) throws ParseException {
        int flag=seminarService.updateKlassSeminarBySeminarIdAndKlassId(klassId,seminarId,klassMap);
        if(flag>0) return true;
        else return  false;
    }


    @DeleteMapping("/seminar/{seminarId}/klass/{klassId}")
    public boolean deleteKlassSeminarBySeminarIdAndKlassId(@PathVariable("klassId") BigInteger klassId,@PathVariable("seminarId") BigInteger seminarId){
        int flag=seminarService.deleteKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
        if(flag>0) return true;
        else return false;
    }


    @GetMapping("seminar/{seminarId}/klass/{klassId}")
    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(@PathVariable("klassId") BigInteger klassId,@PathVariable("seminarId") BigInteger seminarId){
        return seminarService.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);

    }

    @PutMapping("seminar/{seminarId}/status")
    public boolean updateKlassSeminarStatus(@PathVariable("seminarId") BigInteger seminarId,@RequestBody Map<String,Object> klassMap){
        int flag=seminarService.updateKlassSeminarStatus(seminarId,klassMap);
        if(flag>0)return true;
        else return  false;
    }


    @GetMapping("seminar/{seminarId}/team/{teamId}/seminarScore")
    public SeminarScoreVO getTeamSeminarScoreByTeamIdAndSeminarId(@PathVariable("teamId") BigInteger teamId,@PathVariable("seminarId") BigInteger seminarId){
        return seminarService.getTeamSeminarScoreByTeamIdAndSeminarId(teamId,seminarId);
    }

    @PutMapping("seminar/{seminarId}/team/{teamId}/seminarScore")
    public boolean updateSeminarScoreBySeminarIdAndTeamId(@PathVariable("seminarId") BigInteger seminarId,
                                                          @PathVariable("teamId") BigInteger teamId,@RequestBody Map<String,Object> seminarScoreMap){
        int flag=seminarService.updateSeminarScoreBySeminarIdAndTeamId(seminarId,teamId,seminarScoreMap);
        if(flag>0) return true;
        else return false;
    }


    @GetMapping("seminar/{seminarId}/klass/{klassId}/seminarScore")
    public List<SeminarScoreVO> listKlassSeminarScoreByKlassIdAndSeminarId(@PathVariable("klassId") BigInteger klassId,@PathVariable("seminarId")BigInteger seminarId){
        return  seminarService.listKlassSeminarScoreByKlassIdAndSeminarId(klassId,seminarId);
    }
}
