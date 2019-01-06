package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.vo.*;
import xmu.ghct.crm.dao.ShareDao;
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
@CrossOrigin
@RestController
public class ShareController {
    @Autowired
    CourseService courseService;
    @Autowired
    ShareService shareService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ShareDao shareDao;

    /**
     * 共享设置界面：路径上多传入一个courseId，已同意的，共享组队请求+共享讨论课请求信息，包括本课程是主还是从
     * @return
     */
    @GetMapping(value="/share/{courseId}/successShare")
    public List<ShareVO> getSuccessShare(@PathVariable String courseId) throws NotFoundException {
        return shareService.getAllSuccessShare(new BigInteger(courseId));
    }

    /**
     * 共享设置界面：取消某一个共享
     * @return
     */
    @DeleteMapping(value="/share/deleteShare/{shareId}")
    public boolean deleteShare(@PathVariable String shareId,@RequestParam int shareType) throws NotFoundException {
       return shareService.deleteShare(new BigInteger(shareId),shareType);
    }


    /**
     * 代办界面：教师查找个人所有课程下，未处理状态的，共享组队、讨论课请求的信息  he  发给自己的非法组队申请
     * teacherId：通过jwt获得
     * @return
     */
    @GetMapping(value="/share/getUntreatedShare")
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
    @PutMapping("/share/dealShare")
    public boolean dealShare(@RequestBody Map<String,Object> inMap) throws NotFoundException {
        //共享申请信息的id
        BigInteger shareId=new BigInteger(inMap.get("shareId").toString());
        //共享类型，1共享组队，2共享讨论课
        int type=(int)inMap.get("type");
        //处理：1同意，0不同意
        int status=(int)inMap.get("status");
        return shareService.dealShare(shareId,type,status);
    }


    /**
     * 代办处理界面：教师同意/拒绝非法组队申请，teamValidId，status（1同意，0拒绝）
     * 修改队伍状态
     * @return
     */
    @PutMapping("/team/teamValidRequest/deal")
    public boolean dealTeamValidRequest(@RequestBody Map<String,Object> inMap)
    {
        BigInteger teamValidId=new BigInteger(inMap.get("teamValidId").toString());
        int status=(int)inMap.get("status");
        return shareService.dealTeamValidRequest(teamValidId,status);
    }

    /**
     * 进入新增共享界面调用的api，返回所有课程
     */
    @GetMapping("/share/showSendShare")
    public List<CourseWithTeacherVO> showSendCourse(@RequestParam long courseId) throws NotFoundException {
        return shareService.showSendCourse(BigInteger.valueOf(courseId));
    }

    /**
     * 新增共享界面：教师向教师发送一个共享组队/共享讨论课请求信息，发送者默认为主课程，type=1组队0讨论课，subCourseId, subCourseTeacherId
     * @return
     */
    @PostMapping("/share/sendShare")
    public boolean sendShare(@RequestBody Map<String,Object> inMap){
        Share share=new Share();
        share.setMainCourseId(new BigInteger(inMap.get("mainCourseId").toString()));
        share.setSubCourseId(new BigInteger(inMap.get("subCourseId").toString()));
        share.setSubCourseTeacherId(new BigInteger(inMap.get("subCourseTeacherId").toString()));
        //判断自己课程是否已成为其他课程的从课程，若是则不能发起
        int type=(int)inMap.get("type");
        if(type==1)
        {
            Share share1=shareDao.getSubTeamShare(share.getMainCourseId());
            if(share1!=null) {
                return false;
            }
            else {
                share.setShareType("共享组队");
            }
        }
        else if(type==2)
        {
            Share share1=shareDao.getSubSeminarShare(share.getMainCourseId());
            if(share1!=null) {
                return false;
            }
            else{
                share.setShareType("共享讨论课");
            }
        }
        else{
            return false;
        }
        return shareService.launchShareRequest(share);
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
