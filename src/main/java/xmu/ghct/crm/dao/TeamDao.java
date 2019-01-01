package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareTeamVO;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.CourseMapper;
import xmu.ghct.crm.mapper.KlassMapper;
import xmu.ghct.crm.mapper.StudentMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDao {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    KlassMapper klassMapper;

    /**
     * 根据courseId获得未组队学生，先找全部学生的id，再找组队学生的id，取交集，根据id查学生信息
     * @param courseId
     * @return
     */
    public List<StudentVO> getNoTeamStudentByCourseId(BigInteger courseId) {
        List<BigInteger> allStudentId=studentMapper.getAllStudentIdByCourseId(courseId);
        //查course下所有klass
        List<Klass> allKlass=klassMapper.listKlassByCourseId(courseId);
        //查klass下所有team
        List<BigInteger> teamStudentId=new ArrayList<>();//course下所有klass下所有team，team下所有studentId
        for(Klass klassItem:allKlass)
        {
            List<Team> teams=listTeamByKlassId(klassItem.getKlassId());
            for(Team teamItems: teams){
                List<BigInteger> studentsId=teamMapper.getStudentIdByTeamId(teamItems.getTeamId());
                for(BigInteger i:studentsId)
                    teamStudentId.add(i);
            }
        }
        //取差集，找未组队学生id
        allStudentId.removeAll(teamStudentId);
        List<BigInteger> noTeamStudentId=allStudentId;
        //查未组队学生的信息
        List<StudentVO> noTeamStudent=new ArrayList<>();
        for(BigInteger id:noTeamStudentId)
        {
            User user=studentMapper.getStudentByStudentId(id);
            StudentVO studentVO=new StudentVO();
            studentVO.setStudentId(id);
            studentVO.setAccount(user.getAccount());
            studentVO.setName(user.getName());
            noTeamStudent.add(studentVO);
        }
        return noTeamStudent;
    }

    public  List<BigInteger> getStudentIdByTeamId(BigInteger teamId){
        return teamMapper.getStudentIdByTeamId(teamId);
    }

    public Team getTeamInfoByTeamId(BigInteger teamId){
        return teamMapper.getTeamInfoByTeamId(teamId);
    }

    public List<Team> listTeamByKlassId(BigInteger klassId) {
        //*********考虑到从课程的关系，要先查klass_team表找到所有的team，再根据teamId查信息
        //注意返回值从课程与主课程klassId不同
        List<BigInteger> teamIds=teamMapper.listTeamIdByKlassId(klassId);
        List<Team> teams=new ArrayList<>();
        for(BigInteger id:teamIds)
        {
            Team team=teamMapper.getTeamInfoByTeamId(id);
            if(team.getKlassId()!=klassId)//如果是从课程，则改变klassId
                team.setKlassId(klassId);
            teams.add(team);
        }
        return teams;
    }

    /**
     * @cyq
     * 根据courseId查找是否有共享分组请求
     * @param courseId
     * @return
     */
    public List<ShareTeamVO> getShareTeamInfoByCourseId(BigInteger courseId){
        return teamMapper.getShareTeamInfoByCourseId(courseId);
    }

    /**
     * @cyq
     * //删除Team表，删除team和student关系，删除课程、班级与team关系
     * @param teamId
     * @return
     */
    public boolean deleteTeam(BigInteger teamId)
    {
        int v1=teamMapper.deleteTeamInfo(teamId);
        int v2=teamMapper.deleteKlassTeam(teamId);
        int v3=teamMapper.deleteStudentTeam(teamId);
        if(v1+v2+v3>0)
            return true;
        else
            return false;
    }

    /**
     * @cyq
     * 删除小组成员（非组长）
     * @param teamId
     * @param studentId
     * @return
     */
    public boolean removeTeamMember(BigInteger teamId,BigInteger studentId) {
        if (teamMapper.removeTeamMember(teamId, studentId) > 0)
            return true;
        else
            return false;
    }


    public BigInteger getKlassIdByTeamId(BigInteger teamId){
        return teamMapper.getKlassIdByTeamId(teamId);
    }


    public BigInteger getCourseIdByTeamId(BigInteger teamId){
        return teamMapper.getCourseIdByTeamId(teamId);
    }

    public boolean addTeamMember(BigInteger teamId,BigInteger studentId)
    {
        int v=teamMapper.addTeamMember(teamId,studentId);
        if(v>0)
            return true;
        else
            return false;
    }

    public int getTeamSerialByTeamId(BigInteger teamId){
        return teamMapper.getTeamSerialByTeamId(teamId);
    }


    /**
     * 获取班级下最大队伍序号
     * @param klassId
     * @return
     */
    public int getMaxTeamSerialOfTeam(BigInteger klassId){
        return teamMapper.getMaxTeamSerialOfTeam(klassId);
    }

    /**
     * 创建队伍
     * @param team
     * @return
     */
    public int insertTeam(Team team){
        return teamMapper.insertTeam(team);
    }

    /**
     * 更新队伍合法状态
     * @param teamId
     * @param status
     * @return
     */
    public int updateStatusByTeamId(BigInteger teamId,int status){
        return teamMapper.updateStatusByTeamId(teamId,status);
    }

    /**
     * 插入班级-队伍关联信息
     * @param klassId
     * @param teamId
     * @return
     */
    public int insertKlassTeam(BigInteger klassId,BigInteger teamId){
        return teamMapper.insertKlassTeam(klassId,teamId);
    }

    /**
     * 插入学生-队伍关联信息
     * @param teamId
     * @param studentId
     * @return
     */
    public int insertTeamStudent(BigInteger teamId,BigInteger studentId){
        return teamMapper.insertTeamStudent(teamId,studentId);
    }


    /**
     * 插入学生-班级-课程关联信息
     * @param klassId
     * @param studentId
     * @param courseId
     * @return
     */
    public int insertKlassStudent(BigInteger klassId,BigInteger studentId,BigInteger courseId){
        return teamMapper.insertKlassStudent(klassId,studentId,courseId);
    }

    /**
     * 获取学生所在队伍ID
     * @param studentId
     * @return
     */
     public BigInteger getTeamIdByStudentId(BigInteger studentId){
        return teamMapper.getTeamIdByStudentId(studentId);
    }

}
