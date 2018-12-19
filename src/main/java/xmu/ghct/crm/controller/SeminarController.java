package xmu.ghct.crm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xmu.ghct.crm.service.SeminarService;

import java.text.ParseException;
import java.util.Map;

@RestController
public class SeminarController {

    @Autowired
    SeminarService seminarService;

    @RequestMapping(value="/seminar",method = RequestMethod.POST)
    public boolean creatSeminar(@RequestBody Map<String,Object> seminarMap) throws ParseException {
        int flag= seminarService.creatSeminar(seminarMap);
        if(flag>0)
        return true;
        else return false;
    }
}
