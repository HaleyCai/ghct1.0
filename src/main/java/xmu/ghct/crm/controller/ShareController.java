package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareTeamVO;
import xmu.ghct.crm.VO.ShareVO;
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

    /**
     * 共享设置界面：根据teacherId获得，已同意的，共享组队请求+共享讨论课请求信息，包括本课程是主还是从
     * @param teacherId
     * @return
     */
    @GetMapping(value="/share/{teacherId}/share")
    public List<ShareVO> getSuccessShare(@PathVariable Long teacherId)
    {
        return shareService.getAllSuccessShare(BigInteger.valueOf(teacherId));
    }

    /**
     * 共享设置界面：取消某一个共享
     * 删除组队共享，同时删除klass_team的关系??
     * 删除讨论课共享，同时删除klass_round的关系??
     * @return
     */
    @DeleteMapping(value="/share/deleteShare/{shareId}")
    public boolean deleteShare(@PathVariable Long shareId,@RequestParam String shareType){
       return shareService.deleteShare(BigInteger.valueOf(shareId),shareType);
    }


    /**
     * 代办界面：教师查找个人所有课程下，未处理状态的，共享组队、讨论课请求的信息
     * teacherId：通过jwt获得
     * @return
     */
    @GetMapping(value="/share/untreatedRequest")
    public List<ShareRequestVO> getUntreatedShare(@RequestParam Long teacherId){
        return shareService.getUntreatedShare(BigInteger.valueOf(teacherId));
    }

    /**
     * 代办界面：教师查找所有，发给自己的非法组队申请
     */
    @GetMapping(value="/team/untreatedRequest")
    public void getUntreatedTeam(){

    }

    /**
     * 代办处理界面：教师同意/拒绝共享分组申请、共享讨论课申请，shareTeamId，type=1组队,0讨论课，status（1同意，0拒绝）
     * 同意的情况下，修改本条记录的状态，并本课程的分组名单要改成主课程的名单
     * 拒绝就只修改该条记录的状态，
     * @param courseId
     * @return
     */



    /**
     * 代办处理界面：教师同意/拒绝非法组队申请，teamValidId，status（1同意，0拒绝）
     * 修改队伍状态
     * @param courseId
     * @return
     */

    /**
     * 新增共享界面：教师向教师发送一个共享组队/共享讨论课请求信息，发送者默认为主课程，type=1组队0讨论课，subCourseId, subCourseTeacherId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/createShare")
    public void createShare(){

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
