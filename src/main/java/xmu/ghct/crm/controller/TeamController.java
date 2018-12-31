package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.VO.TeamSimpleInfo;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * @cyq
     * 教师端+学生端：根据courseId查team的简单信息
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    public List<TeamSimpleInfo> listTeamByCourseId(@PathVariable("courseId") Long courseId){
        return teamService.listTeamByCourseId(BigInteger.valueOf(courseId));
    }

    /**
     * @cyq
     * 教师+学生：根据teamId获取队伍信息
     */
    @RequestMapping(value="/team/{teamId}",method = RequestMethod.GET)
    public TeamInfoVO getTeamInfo(@PathVariable("teamId") String teamId)
    {
        return teamService.getTeamByCourseId(new BigInteger(teamId));
    }

    /**
     * @cyq
     * 学生：获取本课程未组队学生
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/noTeam",method = RequestMethod.GET)
    public List<StudentVO> getNoTeamStudentByCourseId(@PathVariable("courseId")Long courseId){
        return teamService.getNoTeamStudentByCourseId(BigInteger.valueOf(courseId));
    }

    /**
     * 删除队伍、组长解散队伍，删除team表记录，级联删除学生与teamId的关系，删除课程、班级与team的关系，不删除成绩！！
     * @param teamId
     */
    @RequestMapping(value="team/{teamId}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteTeam(@PathVariable("teamId") Long teamId)
    {
        Map<String,Object> map=new HashMap<>();
        if(teamService.deleteTeam(BigInteger.valueOf(teamId)))
            map.put("message",true);
        else map.put("message",false);
        return map;
    }

    /**
     * 将多名学生加入该队伍，传参teamId,studentId，从未组队的学生中选择，不需要判断是否是组长是否已组队！
     * ***判断队伍的状态并修改
     * @param teamId
     * @param studentIdList
     */
    @RequestMapping(value="team/{teamId}/add",method = RequestMethod.PUT)
    public boolean addTeamMember(@PathVariable("teamId") Long teamId,
                              @RequestBody List<BigInteger> studentIdList)
    {
        Boolean flag=teamService.addTeamMember(teamId,studentIdList);
        if(teamService.judgeIllegal(BigInteger.valueOf(teamId))){
            teamService.updateStatusByTeamId(BigInteger.valueOf(teamId),1);
        }
        else {
            teamService.updateStatusByTeamId(BigInteger.valueOf(teamId),0);
        }
        return flag;
    }

    /**
     * 移除成员(一次只能删一个），或自己退出队伍（队长退出，直接解散队伍），退出的同时，修改team和student处的数据
     * ***判断队伍的状态并修改
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="team/{teamId}/remove",method = RequestMethod.PUT)
    public Map<String,Object> removeTeamMember(@PathVariable("teamId") Long teamId,
                                 @RequestBody Map<String,Object> inMap)
    {
        Map<String,Object> map=new HashMap<>();
        int flag=teamService.removeTeamMember(BigInteger.valueOf(teamId),new BigInteger(inMap.get("studentId").toString()));
        if(flag==0){
            map.put("message",false);
        }
        else if(flag==1){
            map.put("message",true);
        }
        else if(flag==2){
            teamService.updateStatusByTeamId(BigInteger.valueOf(teamId),1);
            map.put("message",true);
        }
        else if(flag==3){
                teamService.updateStatusByTeamId(BigInteger.valueOf(teamId),0);
            map.put("message",true);
            }

        return map;
    }

    //在service层写判断小组是否合法的函数！！！
    /**
     * 创建小组，先创建组，初始加入成员为组长，判断是否合法后填写状态
     */
    @RequestMapping(value="/team/create", method = RequestMethod.POST)
    public boolean createTeam(@RequestParam BigInteger studentId,@RequestBody List<List<Map>> creatTeamMap){
        BigInteger teamId=teamService.insertTeam(studentId,creatTeamMap);
        if(teamId!=null){
            int status;
            if(teamService.judgeIllegal(teamId)){
                status=1;
            }
            else {
                status=0;
            }
            teamService.updateStatusByTeamId(teamId,status);
            return true;
        }
        else return false;
    }
}
