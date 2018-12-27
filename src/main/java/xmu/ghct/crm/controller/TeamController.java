package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.VO.TeamSimpleInfo;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.service.TeamService;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    /**
     * @cyq
     * 教师端：根据courseId查team的简单信息
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    public List<TeamSimpleInfo> listTeamByCourseId(@PathVariable("courseId") BigInteger courseId){
        return teamService.listTeamByCourseId(courseId);
    }

    /**
     * @cyq
     * 学生端，根据klassId查team的简单信息
     * @param klassId
     * @return
     */
    @RequestMapping(value="/klass/{klassId}/team", method = RequestMethod.GET)
    public List<TeamSimpleInfo> listTeamByKlassId(@PathVariable("klassId") BigInteger klassId)
    {
        return teamService.listTeamByKlassId(klassId);
    }

    /**
     * @cyq
     * 教师+学生：根据teamId获取队伍信息
     */
    @RequestMapping(value="/team/{teamId}",method = RequestMethod.GET)
    public TeamInfoVO getTeamInfo(@PathVariable("teamId") BigInteger teamId)
    {
        return teamService.getTeamByCourseId(teamId);
    }

    /**
     * @cyq
     * 学生：获取本班未组队学生
     * @param klassId
     * @return
     */
    @RequestMapping(value="/course/{klassId}/noTeam",method = RequestMethod.GET)
    public List<StudentVO> getNoTeamStudentByKlassId(@PathVariable("klassId")BigInteger klassId){
        return teamService.getNoTeamStudentByKlassId(klassId);
    }

    /**
     * 删除队伍、组长解散队伍，删除team表记录，级联删除学生与teamId的关系，删除课程、班级与team的关系，不删除成绩！！
     * @param teamId
     */
    @RequestMapping(value="team/{teamId}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteTeam(@PathVariable("teamId") BigInteger teamId)
    {
        Map<String,Object> map=new HashMap<>();
        if(teamService.deleteTeam(teamId))
            map.put("message",true);
        else map.put("message",false);
        return map;
    }

    /**
     * 将学生加入该队伍，传参teamId,studentId，组队规则判断！！！从未组队的学生中选择，不需要判断是否是组长是否已组队！
     * （两个课程中组队？）
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
    public Map<String,Object> removeTeamMember(@PathVariable("teamId") BigInteger teamId,
                                 @RequestBody Map<String,Object> inMap)
    {
        Map<String,Object> map=new HashMap<>();
        if(teamService.removeTeamMember(teamId,new BigInteger(inMap.get("studentId").toString())))
            map.put("message",true);
        else
            map.put("message",true);
        return map;
    }

}
