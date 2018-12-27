package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.service.ShareService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author caiyq
 */
@RestController
public class ShareController {
    @Autowired
    ShareService shareService;

    ///查看共享组队、共享讨论课、非法组队申请信息

    /**
     * 共享分组界面：根据courseId获得，共享组队请求+共享讨论课请求信息，包括本课程是主还是从
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/share",method = RequestMethod.GET)
    public void getSuccessTeamShare(@PathVariable("courseId") BigInteger courseId)
    {

    }

    /**
     * 共享设置界面：取消共享，删除共享组队、讨论课信息，type=1组队0讨论课，shareId,
     * @return
     */
    @RequestMapping(value="/course/{courseId}/deleteShare/{shareId}",method = RequestMethod.DELETE)
    public void deleteShare(){

    }

    /**
     * 新增共享界面：教师向教师发送一个共享组队/共享讨论课请求信息，发送者默认为主课程，type=1组队0讨论课，subCourseId, subCourseTeacherId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/createShare")
    public void createShare(){

    }

    /**
     * 代办界面：教师查找个人所有课程下，未处理状态共享组队、讨论课请求的简单消息
     * @return
     */
    @RequestMapping(value="/course/sharerequest")
    public void getUntreatedShare(){

    }

    /**
     * 代办处理：教师查找某个共享组队、讨论课请求的详细信息
     * @return
     */
    @RequestMapping(value="/course/sharerequest/{shareRequestId}")
    public void getShareInfo(){

    }

    /**
     * 代办界面：教师查找发送给个人、未处理状态的非法组队请求
     */
    @RequestMapping(value = "/course/request/teamvalid")
    public void getUntreatedTeamValid(){

    }


    /**
     * 代办处理界面：教师同意/拒绝共享分组申请、共享讨论课申请，shareTeamId，type=1组队,0讨论课，status（1同意，0拒绝）
     * @param courseId
     * @return
     */

    /**
     * 代办处理界面：教师同意/拒绝非法组队申请，teamValidId，status（1同意，0拒绝）
     * @param courseId
     * @return
     */


    @ResponseBody
    public List<Share> getTeamShareMessageByCourseId(@PathVariable("courseId") BigInteger courseId){
        return shareService.listTeamShareMessageByCourseId(courseId);
    }


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

    /**
     * 学生向老师发送非法组队申请
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="/team/{teamId}/teamvalidrequest",method = RequestMethod.POST)
    public void sentValidTeamRequest(@PathVariable("teamId") BigInteger teamId,
                                     @RequestBody Map<String,Object> inMap)
    {
        TeamApplicationVO teamApplicationVO=new TeamApplicationVO();
        teamApplicationVO.setTeamId(teamId);
        teamApplicationVO.setReason(inMap.get("reason").toString());
        teamApplicationVO.setStatus(0);//未处理
        //根据courseId获得teacherId

    }
}
