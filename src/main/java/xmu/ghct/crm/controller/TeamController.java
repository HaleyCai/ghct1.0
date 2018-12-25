package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.service.TeamService;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    @ResponseBody
    public List<TeamInfoVO> getTeamInfoByCourseId(@PathVariable("courseId") BigInteger courseId){
        return teamService.listTeamInfoByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/noTeam",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getNoTeamStudentByCourseId(@PathVariable("courseId")BigInteger courseId){
        return teamService.getNoTeamStudentByCourseId(courseId);
    }

    /**
     * @cyq
     * 根据teamId获取队伍信息
     */
    @RequestMapping(value="/team/{teamId}",method = RequestMethod.GET)
    public void getTeamInfo(@PathVariable("teamId") BigInteger teamId)
    {

    }

    /**
     * 修改队伍信息
     * @param teamId
     */
    @RequestMapping(value="/team/{teamId}",method = RequestMethod.PUT)
    public void modifyInfo(@PathVariable("teamId") BigInteger teamId)
    {

    }

    /**
     * 删除队伍、组长解散队伍
     * @param teamId
     */
    @RequestMapping(value="team/{teamId}",method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable("teamId") BigInteger teamId)
    {

    }

    /**
     * 将学生加入该队伍，传参teamId,studentId
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="team/{teamId}/add",method = RequestMethod.PUT)
    public void addTeamMumber(@PathVariable("teamId") BigInteger teamId,
                              @RequestBody Map<String,Object> inMap)
    {

    }

    /**
     * 移除成员，或自己退出队伍（队长退出，直接解散队伍），退出的同时，修改team和student处的数据
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="team/{teamId}/remove",method = RequestMethod.PUT)
    public void removeTeamMember(@PathVariable("teamId") BigInteger teamId,
                                 @RequestBody Map<String,Object> inMap)
    {

    }

    /**
     * 向老师发送非法组队申请
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="/team/{teamId}/teamvalidrequest",method = RequestMethod.POST)
    public void validTeamRequest(@PathVariable("teamId") BigInteger teamId,
                                 @RequestBody Map<String,Object> inMap)
    {

    }

    /**
     * 教师同意非法组队
     * @param teamId
     */
    @RequestMapping(value="/team/{teamId}/approve")
    public void teacherApproveValidTeam(@PathVariable("teamId") BigInteger teamId)
    {

    }
}
