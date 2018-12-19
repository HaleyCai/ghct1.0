package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.service.SeminarService;

import java.math.BigInteger;
import java.util.List;

@RestController
public class SeminarController {

    @Autowired
    private SeminarService seminarService;
    /**
     * 根据轮次id获取该轮次下所有的讨论课Seminar
     * @param roundId
     * @return
     */
    @RequestMapping(value="round/{roundId}/seminar",method = RequestMethod.GET)
    @ResponseBody
    public List<Seminar> getSeminarByRoundId(@PathVariable("roundId") BigInteger roundId)
    {
        return seminarService.getSeminarByRoundId(roundId);
    }
}
