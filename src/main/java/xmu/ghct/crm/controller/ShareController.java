package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareTeamVO;
import xmu.ghct.crm.VO.ShareVO;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.CourseService;
import xmu.ghct.crm.service.ShareService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caiyq
 */
@RestController
public class ShareController {
    @Autowired
    CourseService courseService;
    @Autowired
    ShareService shareService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 共享设置界面：根据teacherId获得，已同意的，共享组队请求+共享讨论课请求信息，包括本课程是主还是从
     * @return
     */
    @GetMapping(value="/share/successShare")
    public List<ShareVO> getSuccessShare(HttpServletRequest request) throws NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        return shareService.getAllSuccessShare(id);
    }

    /**
     * 共享设置界面：取消某一个共享
     * @return
     */
    @DeleteMapping(value="/share/deleteShare/{shareId}")
    public boolean deleteShare(@PathVariable String shareId,@RequestParam String shareType) throws NotFoundException {
       return shareService.deleteShare(new BigInteger(shareId),shareType);
    }


    /**
     * 代办界面：教师查找个人所有课程下，未处理状态的，共享组队、讨论课请求的信息  he  发给自己的非法组队申请
     * teacherId：通过jwt获得
     * @return
     */
    @GetMapping(value="/request/untreatedRequest")
    public Map<String,Object> getUntreatedShare(HttpServletRequest request) throws NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<ShareRequestVO> shareRequestVOS=shareService.getUntreatedShare(id);
        List<TeamApplicationVO> teamApplicationVOS=shareService.getUntreatedTeam(id);
        Map<String,Object> map=new HashMap<>();
        map.put("share",shareRequestVOS);
        map.put("team",teamApplicationVOS);
        return map;
    }


    /**
     * 代办处理界面：教师同意/拒绝共享分组申请、共享讨论课申请，shareTeamId，type=1组队,0讨论课，status（1同意，0拒绝）
     * 同意的情况下，修改本条记录的状态，并本课程的分组名单要改成主课程的名单
     * 拒绝就只修改该条记录的状态，
     * @return
     */



    /**
     * 代办处理界面：教师同意/拒绝非法组队申请，teamValidId，status（1同意，0拒绝）
     * 修改队伍状态
     * @return
     */

    /**
     * 进入新增共享界面调用的api，返回所有课程
     */
    @GetMapping("/share/showSendShare")
    public void showSendShare(){

    }

    /**
     * 新增共享界面：教师向教师发送一个共享组队/共享讨论课请求信息，发送者默认为主课程，type=1组队0讨论课，subCourseId, subCourseTeacherId
     * @return
     */
    @PostMapping("/share/sendShare")
    public void sendShare(){

    }

    /**
     * 学生向老师发送非法组队申请
     * @param inMap
     */
    @PostMapping("/team/teamValidRequest")
    public boolean sendValidTeamRequest(@RequestBody Map<String,Object> inMap) throws NotFoundException {
        BigInteger courseId=new BigInteger(inMap.get("courseId").toString());
        BigInteger teacherId=courseService.getCourseByCourseId(courseId).getTeacherId();
        TeamApplicationVO teamApplicationVO=new TeamApplicationVO();
        teamApplicationVO.setTeamId(new BigInteger(inMap.get("teamId").toString()));
        teamApplicationVO.setTeacherId(teacherId);
        teamApplicationVO.setReason(inMap.get("reason").toString());
        return shareService.sentValidTeamRequest(teamApplicationVO);
    }
}
