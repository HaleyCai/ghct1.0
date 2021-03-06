package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.vo.*;
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

/**
 * @author caiyq
 */
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

    @Autowired
    ShareDao shareDao;
    /**
     * @cyq
     * 查课程下所有队伍的简单信息
     * @param courseId
     * @return
     */
    public List<TeamSimpleInfo> listTeamByCourseId(BigInteger courseId) throws NotFoundException {
        List<Klass> klasses=klassDao.listKlassByCourseId(courseId);
        List<Team> teamList=new ArrayList<>();
        for(Klass item:klasses){
            teamList.addAll(teamDao.listTeamByKlassId(item.getKlassId()));
        }
        List<TeamSimpleInfo> teamSimpleInfoList=new ArrayList<>();
        for(Team teamItem:teamList){
            TeamSimpleInfo teamSimpleInfo=new TeamSimpleInfo();
            teamSimpleInfo.setTeamId(teamItem.getTeamId());
            teamSimpleInfo.setTeamName(teamItem.getTeamName());
            teamSimpleInfo.setTeamSerial(teamItem.getKlassSerial()+"-"+teamItem.getTeamSerial());
            teamSimpleInfo.setStatus(teamItem.getStatus());
            teamSimpleInfoList.add(teamSimpleInfo);
        }
        return teamSimpleInfoList;
    }


    /**
     * @cyq
     * 某一队伍的信息，包括简单信息和组长、成员信息
     * @param courseId
     * @param teamId
     * @return
     */
    public TeamInfoVO getTeamByCourseId(BigInteger courseId,BigInteger teamId) throws NotFoundException {
        List<BigInteger> klassIds=courseDao.listKlassIdByCourseId(courseId);
        List<BigInteger> teamIds=new ArrayList<>();
        for(BigInteger klassId:klassIds)
        {
            teamIds.addAll(teamDao.listTeamIdByKlassId(klassId));
        }
        TeamInfoVO teamInfoVO=new TeamInfoVO();

        if(teamIds.contains(teamId))
        {
            //该课程下有该队伍，才查队伍信息
            Team team=teamDao.getTeamInfoByTeamId(teamId);
            if(team==null)
            {
                return null;
            }
            teamInfoVO.setTeamId(team.getTeamId());
            teamInfoVO.setTeamName(team.getTeamName());
            teamInfoVO.setTeamSerial(team.getTeamSerial());
            teamInfoVO.setKlassId(team.getKlassId());
            teamInfoVO.setKlassSerial(team.getKlassSerial());
            teamInfoVO.setStatus(team.getStatus());
            System.out.println("status "+team.getStatus());

            //查该课程下的全部学生，排除掉队伍中非本课程的学生
            User leader=studentDao.getStudentById(team.getLeaderId());
            StudentVO studentVO=new StudentVO();
            studentVO.setAccount(leader.getAccount());
            studentVO.setEmail(leader.getEmail());
            studentVO.setName(leader.getName());
            studentVO.setStudentId(leader.getId());
            studentVO.setTeamId(leader.getTeamId());
            teamInfoVO.setTeamLeader(studentVO);
            System.out.println("Leader=="+studentVO);

            //查询组员信息
            List<BigInteger> studentIdList=teamDao.getStudentIdByTeamId(teamId);
            List<StudentVO> members=new ArrayList<>();
            for(BigInteger studentIdItem:studentIdList){
                if(studentIdItem.equals(team.getLeaderId())){
                    continue;
                }
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
        return null;
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
    public int removeTeamMember(BigInteger teamId,BigInteger studentId,BigInteger userId) throws NotFoundException {
        Team team=teamDao.getTeamInfoByTeamId(teamId);
        if(team.getLeaderId().equals(studentId)){
            if(userId.equals(team.getLeaderId())){
                teamDao.deleteTeam(teamId);
                return 1;
            }
            else {
                return 0;
            }
        }
        else{
            boolean flag = teamDao.removeTeamMember(teamId,studentId);
            if(flag) {
                if(judgeIllegal(teamId)) {
                    return 2;
                } else {
                    return 3;
                }
            }
            else {
                return 0;
            }
        }

    }

    /**
     * 加入小组成员，先查有是否超过人数，再加入，再判断当前队伍状态
     * @param teamId
     * @param studentIdList
     * @return
     */
    public boolean addTeamMember(BigInteger teamId,List<BigInteger> studentIdList)throws NotFoundException{
        List<BigInteger> studentIdS=teamDao.getStudentIdByTeamId(teamId);
        System.out.println(studentIdS);
        for(BigInteger item:studentIdList){
            System.out.println(item);
            boolean flag=false;
            if(studentIdS!=null&&studentIdS.size()>0){
                for(BigInteger studentId:studentIdS) {
                    System.out.println(studentId);
                    if (studentId.equals(item)) {
                        System.out.println(studentId);
                        flag=true;
                        break;
                    }
                }
                if(!flag)  teamDao.insertTeamStudent(teamId,item);
                }
            else {
                teamDao.insertTeamStudent(teamId,item);
            }
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
    public BigInteger insertTeam(BigInteger studentId, CreatTeamVO creatTeamVO) throws NotFoundException, ParamErrorException{
        System.out.println(studentId);
        List<BigInteger> teamIdList = teamDao.listTeamIdByStudentId(studentId);
        for (BigInteger teamId : teamIdList) {
            BigInteger courseId = teamDao.getCourseIdByTeamId(teamId);
            if (courseId.equals(creatTeamVO.getCourseId())) {
                System.out.println("学生已组队！");
                return null;
            }
        }
        System.out.println(studentId);
        List<BigInteger> studentIdList = creatTeamVO.getStudentIdList();
        List<TeamStrategyVO> teamStrategyVOList = strategyDao.listTeamStrategyByCourseId(creatTeamVO.getCourseId());
        for (TeamStrategyVO item : teamStrategyVOList) {
            if ("ConflictCourseStrategy".equals(item.getStrategyName())) {
                List<BigInteger> conflictCourseIdList = strategyDao.listConflictCourseId(item.getStrategyId());
                List<BigInteger> conflictStudentIdList = new ArrayList<>();
                for (BigInteger courseIdItem : conflictCourseIdList) {
                    for (BigInteger studentIdItem : studentIdList) {
                        if (strategyDao.getCourseStudentNumber(courseIdItem, studentIdItem) > 0) {
                            if (conflictStudentIdList.size() > 0) {
                                for (BigInteger conflictStudentId : conflictStudentIdList) {
                                    if (studentIdItem.equals(conflictStudentId)) {
                                        System.out.println("存在学生选修组队冲突的课程！");
                                        throw new ParamErrorException("存在学生选修组队冲突的课程！");
                                    }
                                }
                            }
                            conflictStudentIdList.add(studentIdItem);
                        }
                    }
                }
            }
        }
        Team team = new Team();
        int teamSerial = teamDao.getMaxTeamSerialOfTeam(creatTeamVO.getKlassId()) + 1;
        team.setKlassId(creatTeamVO.getKlassId());
        Klass klass = klassDao.getKlassByKlassId(creatTeamVO.getKlassId());
        team.setCourseId(creatTeamVO.getCourseId());
        team.setTeamSerial(teamSerial);
        team.setLeaderId(studentId);
        team.setTeamName(creatTeamVO.getTeamName());
        team.setKlassSerial(klass.getKlassSerial());
        int flag = teamDao.insertTeam(team);
        int flag1 = teamDao.insertKlassTeam(creatTeamVO.getKlassId(), team.getTeamId());
        int flag2 = teamDao.insertTeamStudent(team.getTeamId(), studentId);
        int size = studentIdList.size();
        System.out.println(size);
        while (size > 0) {
            teamDao.insertTeamStudent(team.getTeamId(), studentIdList.get(size - 1));
            size--;
        }
        if (flag > 0 && flag1 > 0 && flag2 > 0) {
            return team.getTeamId();
        } else {
            return null;
        }
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
     * @author hzm
     * 判断队伍有效性
     * @param teamId
     * @return
     */
    public boolean judgeIllegal(BigInteger teamId) throws NotFoundException {
        BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
        List<BigInteger> studentIdList=strategyDao.listStudentIdByTeamId(teamId);
        List<TeamStrategyVO> teamStrategyVOList=strategyDao.listTeamStrategyByCourseId(courseId);
        for(TeamStrategyVO item:teamStrategyVOList){
            if("TeamAndStrategy".equals(item.getStrategyName())){
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectAndStrategy(item.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                    if("MemberLimitStrategy".equals(andOrOrStrategyVO.getStrategyName())){
                        int teamMemberNumber=strategyDao.getTeamMemberNumber(teamId);
                        CourseVO courseVO=strategyDao.getTeamMemberLimit(item.getStrategyId());
                        if(teamMemberNumber>courseVO.getMaxMember()||teamMemberNumber<courseVO.getMinMember()) {
                            return false;
                        }
                    }
                    else if("TeamOrStrategy".equals(andOrOrStrategyVO.getStrategyName())){
                        List<AndOrOrStrategyVO> orStrategyVOList=strategyDao.selectAndStrategy(andOrOrStrategyVO.getStrategyId());
                        boolean flag=false;
                        for(AndOrOrStrategyVO orStrategyVO:orStrategyVOList){
                            CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(orStrategyVO.getStrategyId());
                            int studentNumber=0;
                            for(BigInteger studentId:studentIdList){
                                studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                            }
                            if(studentNumber<courseLimitVO.getMaxMember()&&studentNumber>courseLimitVO.getMinMember()) {flag=true;break;}
                        }
                        if(flag==true) {
                            continue;
                        } else {
                            return false;
                        }

                    }
                    else if("CourseMemberLimitStrategy".equals(andOrOrStrategyVO.getStrategyName())){
                        CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(andOrOrStrategyVO.getStrategyId());
                        int studentNumber=0;
                        for(BigInteger studentId:studentIdList){
                            studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                        }
                        if(studentNumber>courseLimitVO.getMaxMember()||studentNumber<courseLimitVO.getMinMember()) {
                            return false;
                        }
                    }

                }
            }else if("TeamOrStrategy".equals(item.getStrategyName())){
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
                if(flag=true) {
                    continue;
                } else {
                    return false;
                }
            }
            else if("ConflictCourseStrategy".equals(item.getStrategyName())){
                System.out.println("ConflictCourseStrategy");
                List<BigInteger> conflictCourseIdList=strategyDao.listConflictCourseId(item.getStrategyId());
                List<BigInteger> conflictStudentIdList=new ArrayList<>();
                for(BigInteger courseIdItem:conflictCourseIdList){
                    for(BigInteger studentId:studentIdList){
                        if(strategyDao.getCourseStudentNumber(courseIdItem,studentId)>0){
                            if(conflictStudentIdList.size()>0){
                                for(BigInteger conflictStudentId:conflictStudentIdList){
                                    if(studentId.equals(conflictStudentId)){
                                        System.out.println("存在学生选修组队冲突的课程！");
                                        return false;}
                                }
                            }
                            conflictStudentIdList.add(studentId);
                        }
                    }
                }
            }
        }
        return true;
    }

   public Map<String,Object> getUserTeamStatusById(BigInteger courseId,BigInteger id) throws NotFoundException {
       Map<String,Object> map=new HashMap<>(16);
       List<BigInteger> teamIdList=teamDao.listTeamIdByStudentId(id);
       System.out.println("teamIdList "+teamIdList);
       if(teamIdList.isEmpty()){
           map.put("isTeam",false);
           map.put("myTeamId",null);
       }
       else{
           map.put("isTeam",true);
           map.put("myTeamId",teamIdList.get(0));
           return map;
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
