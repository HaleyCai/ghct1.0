package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.exception.ParamErrorException;

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
    public List<TeamSimpleInfo> listTeamByCourseId(BigInteger courseId) throws NotFoundException {
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
    public List<TeamSimpleInfo> teamTOTeamSimpleInfo(List<Team> teamList) throws NotFoundException {
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
    public List<StudentVO> getNoTeamStudentByCourseId(BigInteger courseId) throws NotFoundException {
        return teamDao.getNoTeamStudentByCourseId(courseId);
    }

    /**
     * @cyq
     * 级联删除队伍
     * @param teamId
     * @return
     */
    public boolean deleteTeam(BigInteger teamId) throws NotFoundException {
        return teamDao.deleteTeam(teamId);
    }

    /**
     * @cyq
     * 退出队伍、移除成员
     * @param teamId
     * @param studentId
     * @return
     */
    public int removeTeamMember(BigInteger teamId,BigInteger studentId) throws NotFoundException {
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
    public BigInteger getKlassIdByTeamId(BigInteger teamId) throws NotFoundException {
        return teamDao.getKlassIdByTeamId(teamId);
   }

    /**
     * 根据teamId获取队伍信息
     * @param teamId
     * @return
     */
   public Team getTeamInfoByTeamId(BigInteger teamId) throws NotFoundException {
        return teamDao.getTeamInfoByTeamId(teamId);
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
    public BigInteger insertTeam(BigInteger studentId, CreatTeamVO creatTeamVO) throws NotFoundException, ParamErrorException {
        List<BigInteger> teamIdList = teamDao.listTeamIdByStudentId(studentId);
        for(BigInteger teamId:teamIdList){
            BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
            if(courseId.equals(creatTeamVO.getCourseId())){
                System.out.println("学生已组队！");
                return null;
            }
        }
        System.out.println(studentId);
        List<BigInteger> studentIdList = creatTeamVO.getStudentIdList();
        List<TeamStrategyVO> teamStrategyVOList = strategyDao.listTeamStrategyByCourseId(creatTeamVO.getCourseId());
        TeamStrategyVO teamStrategyVO = new TeamStrategyVO();
        for (TeamStrategyVO item : teamStrategyVOList) {
            if (item.getStrategyName().equals("ConflictCourseStrategy")) {
                teamStrategyVO = item;
            }
        }

        if (teamStrategyVO.getStrategyName() != null) {
            List<BigInteger> conflictCourseIdList = strategyDao.listConflictCourseId(teamStrategyVO.getStrategyId());
            for (BigInteger courseIdItem : conflictCourseIdList) {
                for (BigInteger studentIdItem : studentIdList) {
                    if (strategyDao.getCourseStudentNumber(courseIdItem, studentIdItem) > 0) {
                        throw new ParamErrorException("学生组队出现课程冲突！");
                    }
                }
            }
        }
        Team team = new Team();
        int teamSerial = teamDao.getMaxTeamSerialOfTeam(creatTeamVO.getKlassId())+1;
        team.setKlassId(creatTeamVO.getKlassId());
        team.setCourseId(creatTeamVO.getCourseId());
        team.setTeamSerial(teamSerial);
        team.setLeaderId(studentId);
        team.setTeamName(creatTeamVO.getTeamName());
        team.setKlassSerial(creatTeamVO.getKlassSerial());
        int flag = teamDao.insertTeam(team);
        int flag_1 = teamDao.insertKlassTeam(creatTeamVO.getKlassId(), team.getTeamId());
        int flag_2 = teamDao.insertTeamStudent(team.getTeamId(), studentId);
        int size = studentIdList.size();
        System.out.println(size);
        while (size > 0) {
            teamDao.insertTeamStudent(team.getTeamId(), studentIdList.get(size - 1));
            size--;
        }
        if (flag > 0 && flag_1 > 0 && flag_2 > 0) {
            System.out.println(flag + "**" + flag_1 + "**" + flag_2);
            return team.getTeamId();
        } else return null;

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
    public boolean judgeIllegal(BigInteger teamId) throws NotFoundException {
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

   public Map<String,Object> getUserTeamStatusById(BigInteger courseId,BigInteger id) throws NotFoundException {
       Map<String,Object> map=new HashMap<>();
       List<BigInteger> teamIdList=teamDao.listTeamIdByStudentId(id);
       if(teamIdList!=null){
           for(BigInteger teamId:teamIdList){
               BigInteger courseIdItem=teamDao.getCourseIdByTeamId(teamId);
               if(courseId.equals(courseIdItem))
               {
                   map.put("isTeam",true);
                   map.put("myTeamId",teamId);
                   return map;
               }
           }
       }
       else{
           map.put("isTeam",false);
           map.put("myTeamId",null);
       }
       return map;
   }


    public  List<BigInteger> listKlassIdByTeamId(BigInteger teamId){
        return teamDao.listKlassIdByTeamId(teamId);
    }

    public  List<BigInteger> listTeamIdByStudentId(BigInteger studentId) throws NotFoundException {
        return teamDao.listTeamIdByStudentId(studentId);
    }

    public BigInteger getCourseIdByTeamId(BigInteger teamId) throws NotFoundException {
        return teamDao.getCourseIdByTeamId(teamId);
    }




}
