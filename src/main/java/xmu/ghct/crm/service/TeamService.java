package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.exception.NotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    TeamDao teamDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    StrategyDao strategyDao;
    /**
     * @cyq
     * 查课程下所有队伍的简单信息
     * @param courseId
     * @return
     */
    public List<TeamSimpleInfo> listTeamByCourseId(BigInteger courseId) {
        //查course下所有klass,再查klass下所有team
        List<Klass> klasses=klassDao.listKlassByCourseId(courseId);
        List<Team> teamList=new ArrayList<>();
        for(Klass item:klasses)
            teamList.addAll(teamDao.listTeamByKlassId(item.getKlassId()));
        return teamTOTeamSimpleInfo(teamList);
    }

    /**
     * @cyq
     * Team对象的list转为TeamSimpleInfo的list
     * @param teamList
     * @return
     */
    public List<TeamSimpleInfo> teamTOTeamSimpleInfo(List<Team> teamList)
    {
        List<TeamSimpleInfo> teamSimpleInfoList=new ArrayList<>();
        for(Team teamItem:teamList){
            TeamSimpleInfo teamSimpleInfo=new TeamSimpleInfo();
            teamSimpleInfo.setTeamId(teamItem.getTeamId());
            teamSimpleInfo.setTeamName(teamItem.getTeamName());
            //队伍的序号是班号-队伍号
            //根据klassId查klassSerial
            int klassSerial=klassDao.getKlassByKlassId(teamItem.getKlassId()).getKlassSerial();
            teamSimpleInfo.setTeamSerial(klassSerial+"-"+teamItem.getTeamSerial());
            teamSimpleInfo.setStatus(teamItem.getStatus());
            teamSimpleInfoList.add(teamSimpleInfo);
        }
        return teamSimpleInfoList;
    }

    /**
     * @cyq
     * 某一队伍的信息，包括简单信息和组长、成员信息
     * @param teamId
     * @return
     */
    public TeamInfoVO getTeamByCourseId(BigInteger teamId) throws NotFoundException {
        TeamInfoVO teamInfoVO=new TeamInfoVO();
        Team team=teamDao.getTeamInfoByTeamId(teamId);
        teamInfoVO.setTeamId(team.getTeamId());
        teamInfoVO.setTeamName(team.getTeamName());
        teamInfoVO.setTeamSerial(team.getTeamSerial());
        teamInfoVO.setKlassId(team.getKlassId());
        teamInfoVO.setKlassSerial(team.getKlassSerial());
        teamInfoVO.setStatus(team.getStatus());
        //查询组长信息
        StudentVO studentVO=new StudentVO();
        User leader=studentDao.getStudentById(team.getLeaderId());
        studentVO.setAccount(leader.getAccount());
        studentVO.setEmail(leader.getEmail());
        studentVO.setName(leader.getName());
        studentVO.setStudentId(leader.getId());
        studentVO.setTeamId(leader.getTeamId());
        teamInfoVO.setTeamLeader(studentVO);
        teamInfoVO.setTeamLeader(studentVO);
        //查询组员信息
        List<BigInteger> studentIdList=teamDao.getStudentIdByTeamId(teamId);
        List<StudentVO> members=new ArrayList<>();
        for(BigInteger studentIdItem:studentIdList){
            if(studentIdItem.equals(team.getLeaderId()))
                continue;
            User student=studentDao.getStudentById(studentIdItem);
            StudentVO member=new StudentVO();
            member.setName(student.getName());
            member.setStudentId(student.getId());
            member.setEmail(student.getEmail());
            member.setAccount(student.getAccount());
            member.setTeamId(student.getTeamId());
            members.add(member);
        }
        teamInfoVO.setMembers(members);
        return teamInfoVO;
    }

    /**
     * @cyq
     * 获取课程里未组队学生
     * @param courseId
     * @return
     */
    public List<StudentVO> getNoTeamStudentByCourseId(BigInteger courseId) {
        return teamDao.getNoTeamStudentByCourseId(courseId);
    }

    /**
     * @cyq
     * 级联删除队伍
     * @param teamId
     * @return
     */
    public boolean deleteTeam(BigInteger teamId)
    {
        return teamDao.deleteTeam(teamId);
    }

    /**
     * @cyq
     * 退出队伍、移除成员
     * @param teamId
     * @param studentId
     * @return
     */
    public int removeTeamMember(BigInteger teamId,BigInteger studentId)
    {
        //*******用jwt中的id判断操作人是否是该队伍组长，若是，继续删除，若不是返回删除失败
        //删除操作，判断删除的student是否是组长，是则解散小组，否则删除成员
        Team team=teamDao.getTeamInfoByTeamId(teamId);
        if(team.getLeaderId()==studentId){
            if(teamDao.deleteTeam(teamId)) return 1;
            else return 0;
        }
        else{
            boolean flag= teamDao.removeTeamMember(teamId,studentId);
            if(flag) {
                if(judgeIllegal(teamId)) return 2;
                else return 3;
            }
            else return 0;
        }

    }

    /**
     * 加入小组成员，先查有是否超过人数，再加入，再判断当前队伍状态
     * @param teamId
     * @param studentIdList
     * @return
     */
    public boolean addTeamMember(BigInteger teamId,List<BigInteger> studentIdList)
    {
        for(BigInteger item:studentIdList){
            if(teamDao.insertTeamStudent(teamId,item)<0) return false;
        }
        return true;
    }


    /**
     * @author hzm
     * 根据teamId获得klassId
     * @param teamId
     * @return
     */
    public BigInteger getKlassIdByTeamId(BigInteger teamId){
        return teamDao.getKlassIdByTeamId(teamId);
   }


    /**
     * 获取班级下最大队伍序号
     * @param klassId
     * @return
     */
    public int getMaxTeamSerialOfTeam(BigInteger klassId){
        return teamDao.getMaxTeamSerialOfTeam(klassId);
    }

    /**
     * 创建队伍
     * @return
     */
    public BigInteger insertTeam(BigInteger studentId, List<List<Map>> creatTeamMap){
        BigInteger teamId=teamDao.getTeamIdByStudentId(studentId);
        if(teamId!=null) {System.out.println("学生已组队！");return null;}
        Team team=new Team();
        Map<String,Object> teamMap=creatTeamMap.get(0).get(0);
        BigInteger klassId=new BigInteger(teamMap.get("klassId").toString());
        int teamSerial=teamDao.getMaxTeamSerialOfTeam(klassId)+1;
        team.setKlassId(klassId);
        BigInteger courseId=new BigInteger(teamMap.get("courseId").toString());
        team.setCourseId(courseId);
        team.setTeamSerial(teamSerial);
        team.setLeaderId(studentId);
        team.setTeamName(teamMap.get("teamName").toString());
        team.setKlassSerial(new Integer(teamMap.get("klassSerial").toString()));
        int flag=teamDao.insertTeam(team);
        int flag_1=teamDao.insertKlassTeam(klassId,team.getTeamId());
        int flag_2=teamDao.insertTeamStudent(team.getTeamId(),studentId);
        List<Map> listMap=creatTeamMap.get(1);
        int size=listMap.size();
        System.out.println(size);
        while(size>0){
            BigInteger memberStudentId=new BigInteger(listMap.get(size-1).get("studentId").toString());
            System.out.println(memberStudentId);
            teamDao.insertTeamStudent(team.getTeamId(),memberStudentId);
            size--;
        }
        if(flag>0&&flag_1>0&&flag_2>0){System.out.println(flag+"**"+flag_1+"**"+flag_2);return team.getTeamId();}
        else return null;
    }

    /**
     * 更新队伍合法状态
     * @param teamId
     * @param status
     * @return
     */
    public int updateStatusByTeamId(BigInteger teamId,int status){
        return teamDao.updateStatusByTeamId(teamId,status);
    }



    /**
     * 判断队伍有效性
     * @param teamId
     * @return
     */
    public boolean judgeIllegal(BigInteger teamId){
        BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
        List<BigInteger> studentIdList=strategyDao.listStudentIdByTeamId(teamId);
        List<TeamStrategyVO> teamStrategyVOList=strategyDao.listTeamStrategyByCourseId(courseId);
        for(TeamStrategyVO item:teamStrategyVOList){
            if(item.getStrategyName().equals("MemberLimitStrategy")){
                System.out.println("MemberLimitStrategy");
                int teamMemberNumber=strategyDao.getTeamMemberNumber(teamId);
                CourseVO courseVO=strategyDao.getTeamMemberLimit(item.getStrategyId());
                if(teamMemberNumber>courseVO.getMaxMember()||teamMemberNumber<courseVO.getMinMember())return false;
            }
            else if(item.getStrategyName().equals("TeamAndStrategy")){
                System.out.println("TeamAndStrategy");
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectAndStrategy(item.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                   CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(andOrOrStrategyVO.getStrategyId());
                   int studentNumber=0;
                   for(BigInteger studentId:studentIdList){
                       studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                   }
                   if(studentNumber>courseLimitVO.getMaxMember()||studentNumber<courseLimitVO.getMinMember()) return false;
                }
            }else if(item.getStrategyName().equals("TeamOrStrategy")){
                System.out.println("TeamOrStrategy");
                boolean flag=false;
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectOrStrategy(item.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                    CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(andOrOrStrategyVO.getStrategyId());
                    int studentNumber=0;
                    for(BigInteger studentId:studentIdList){
                        studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                    }
                    if(studentNumber<courseLimitVO.getMaxMember()&&studentNumber>courseLimitVO.getMinMember()) {flag=true;break;}
                }
                if(flag=true) continue;
                else return false;
            }
            else if(item.getStrategyName().equals("ConflictCourseStrategy")){
                System.out.println("ConflictCourseStrategy");
                List<BigInteger> conflictCourseIdList=strategyDao.listConflictCourseId(item.getStrategyId());
                for(BigInteger courseIdItem:conflictCourseIdList){
                    for(BigInteger studentId:studentIdList){
                        if(strategyDao.getCourseStudentNumber(courseIdItem,studentId)>0)return false;
                    }
                }
            }
        }
        return true;
   }

   public Map<String,Object> getUserTeamStatusById(BigInteger id)
   {
       Map<String,Object> map=new HashMap<>();
       BigInteger teamId=teamDao.getTeamIdByStudentId(id);
       if(teamId!=null)
       {
           map.put("isTeam",true);
           map.put("myTeamId",teamId);
       }
       else
       {
           map.put("isTeam",false);
           map.put("myTeamId",null);
       }
       return map;
   }

}
