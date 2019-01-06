package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.mapper.ShareMapper;
import xmu.ghct.crm.vo.*;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.exception.ParamErrorException;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.CourseService;
import xmu.ghct.crm.service.TeamService;

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
public class TeamController {

    @Autowired
    TeamService teamService;
    @Autowired
    CourseService courseService;
    @Autowired
    ShareMapper shareMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * @cyq
     * 教师端+学生端：根据courseId查team的简单信息
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    public Map<String,Object> listTeamByCourseId(HttpServletRequest request, @PathVariable("courseId") String courseId) throws NotFoundException {
        List<TeamSimpleInfo> teamSimpleInfos=teamService.listTeamByCourseId(new BigInteger(courseId));
        Map<String,Object> map=new HashMap<>(16);
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        map.put("teams",teamSimpleInfos);
        Map<String,Object> team=teamService.getUserTeamStatusById(new BigInteger(courseId),id);
        map.put("isTeam",team.get("isTeam"));
        map.put("myTeamId",team.get("myTeamId"));
        NewCourseVO newCourseVO=courseService.getCourseByCourseId(new BigInteger(courseId));
        map.put("startTime",newCourseVO.getTeamStartTime());
        map.put("endTime",newCourseVO.getTeamEndTime());
        return map;
    }

    /**
     * @cyq
     * 教师端：根据teamId获取队伍信息
     */
    @RequestMapping(value="/course/{courseId}/team/{teamId}",method = RequestMethod.GET)
    public TeamInfoVO getTeamInfo(@PathVariable("courseId") String courseId,
                                  @PathVariable("teamId") String teamId) throws NotFoundException
    {
        return teamService.getTeamByCourseId(new BigInteger(courseId),new BigInteger(teamId));
    }


    /**
     * @cyq
     *学生端：获取队伍信息
     */
    @RequestMapping(value="/course/{courseId}/team/teamInfo",method = RequestMethod.GET)
    public TeamInfoVO getTeamInfo(HttpServletRequest request,
                                  @PathVariable("courseId") String courseId) throws NotFoundException
    {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<BigInteger> teamIdList=teamService.listTeamIdByStudentId(id);
        System.out.println(teamIdList);
        BigInteger teamId=new BigInteger("0");

        BigInteger rightCourseId=new BigInteger("0");
        Share share=shareMapper.getSubTeamShare(new BigInteger(courseId));
        if(share!=null)
        {
            BigInteger mainCourseId=share.getMainCourseId();
            rightCourseId=mainCourseId;
        }
        else{
            rightCourseId=new BigInteger(courseId);
        }
        for(BigInteger teamIdItem:teamIdList){
            Team team=teamService.getTeamInfoByTeamId(teamIdItem);
            System.out.println(team);
            if(rightCourseId.equals(team.getCourseId())) {teamId=teamIdItem;break;}
        }
        TeamInfoVO teamInfoVO=teamService.getTeamByCourseId(new BigInteger(courseId),teamId);
        System.out.println("teamInfoVO "+teamInfoVO);
        return teamInfoVO;
    }

    /**
     * @cyq
     * 学生：获取本课程未组队学生
     * @param courseId
     * @return
     */
    @RequestMapping(value="/course/{courseId}/noTeam",method = RequestMethod.GET)
    public List<StudentVO> getNoTeamStudentByCourseId(@PathVariable("courseId") String courseId) throws NotFoundException {
        return teamService.getNoTeamStudentByCourseId(new BigInteger(courseId));
    }

    /**
     * 删除队伍、组长解散队伍，删除team表记录，级联删除学生与teamId的关系，删除课程、班级与team的关系，不删除成绩！！
     * @param teamId
     */
    @RequestMapping(value="/team/{teamId}",method = RequestMethod.DELETE)
    public boolean deleteTeam(@PathVariable("teamId") String teamId) throws NotFoundException {
        if(teamService.deleteTeam(new BigInteger(teamId))) {
            return true;
        }
        return false;
    }

    /**
     * 将多名学生加入该队伍，传参teamId,studentId，从未组队的学生中选择，不需要判断是否是组长是否已组队！
     * ***判断队伍的状态并修改
     * @param teamId
     * @param creatTeamVO
     */
    @RequestMapping(value="/team/{teamId}/add",method = RequestMethod.PUT)
    public Boolean addTeamMember(@PathVariable("teamId") String teamId,
                              @RequestBody CreatTeamVO creatTeamVO) throws NotFoundException {
        Boolean flag=teamService.addTeamMember(new BigInteger(teamId),creatTeamVO.getStudentIdList());
        if(teamService.judgeIllegal(new BigInteger(teamId))){
            teamService.updateStatusByTeamId(new BigInteger(teamId),1);
        }
        else {
            teamService.updateStatusByTeamId(new BigInteger(teamId),0);
        }
        return flag;
    }

    /**
     * 移除成员(一次只能删一个），或自己退出队伍（队长退出，直接解散队伍），退出的同时，修改team和student处的数据
     * ***判断队伍的状态并修改
     * @param teamId
     * @param inMap
     */
    @RequestMapping(value="/team/{teamId}/remove",method = RequestMethod.PUT)
    public boolean removeTeamMember(HttpServletRequest request,
                                    @RequestBody Map<String,Object> inMap,
                                    @PathVariable("teamId") String teamId) throws NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        int flag=teamService.removeTeamMember(new BigInteger(teamId),new BigInteger(inMap.get("studentId").toString()),id);
        if(flag==0){
            return false;
        }
        else if(flag==1){
            return true;
        }
        else if(flag==2){
            teamService.updateStatusByTeamId(new BigInteger(teamId),1);
            return true;
        }
        else if(flag==3){
                teamService.updateStatusByTeamId(new BigInteger(teamId),0);
                return true;
            }
        return false;
    }

    //在service层写判断小组是否合法的函数！！！
    /**
     * 创建小组，先创建组，初始加入成员为组长，判断是否合法后填写状态
     */
    @RequestMapping(value="/team/create", method = RequestMethod.POST)
    public boolean createTeam(HttpServletRequest request, @RequestBody CreatTeamVO creatTeamVO) throws NotFoundException, ParamErrorException {
        BigInteger studentId=jwtTokenUtil.getIDFromRequest(request);
        BigInteger teamId=teamService.insertTeam(studentId,creatTeamVO);
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
        else {
            return false;
        }
    }


}
