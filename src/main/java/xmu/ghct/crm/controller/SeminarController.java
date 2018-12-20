package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.service.SeminarService;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class SeminarController {

    @Autowired
    private SeminarService seminarService;
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

    @RequestMapping(value="/seminar")
    public Boolean creatSeminar(@RequestBody Map<String,Object> seminarMap) throws ParseException {
        int flag=seminarService.creatSeminar(seminarMap);
        if(flag>0) return true;
        else  return false;
    }


}
