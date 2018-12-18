package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.service.ShareService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
public class ShareController {
    @Autowired
    ShareService shareService;

    @RequestMapping(value="/course/{courseId}/teamShare",method = RequestMethod.GET)
    @ResponseBody
    public List<Share> getTeamShareMessageByCourseId(@PathVariable("courseId") BigInteger courseId){
        return shareService.listTeamShareMessageByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/teamShare/{shareId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteTeamShareByCourseIdAndShareId(@PathVariable("courseId")BigInteger courseId,@PathVariable("shareId")BigInteger shareId){
        int flag=shareService.deleteTeamShareByCourseIdAndShareId(courseId, shareId);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/teamShareRequest",method = RequestMethod.POST)
    @ResponseBody
    public boolean launchTeamShareRequest(@PathVariable("courseId")BigInteger courseId,@RequestBody Map<String,Object> shareMap)  {
        int flag=shareService.launchTeamShareRequest(courseId,shareMap);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/seminarShare",method = RequestMethod.GET)
    @ResponseBody
    public List<Share> getSeminarShareMessageByCourseId(@PathVariable("courseId")BigInteger courseId){
        return shareService.listSeminarShareMessageByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/seminarShare/{shareId}",method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteSeminarShareByCourseIdAndShareId(@PathVariable("courseId")BigInteger courseId,@PathVariable("shareId")BigInteger shareId){
        int flag=shareService.deleteSeminarShareByCourseIdAndShareId(courseId, shareId);
        if(flag>0) return true;
        else return false;
    }

    @RequestMapping(value="/course/{courseId}/seminarShareRequest",method = RequestMethod.POST)
    @ResponseBody
    public boolean launchSeminarShareRequest(@PathVariable("courseId")BigInteger courseId,@RequestBody Map<String,Object> shareMap)  {
        int flag=shareService.launchSeminarShareRequest(courseId,shareMap);
        if(flag>0) return true;
        else return false;
    }
}
