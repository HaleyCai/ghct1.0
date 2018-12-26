package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.CourseMapper;
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
    /**
     * 根据courseId查team的信息
     * @param courseId
     * @return
     */
    public List<Team> listTeamInfoByCourseId(BigInteger courseId) {
        List<Team> teamList = teamMapper.listTeamInfoByCourseId(courseId);
        return teamList;
    }

    /**
     * 根据klassId获得组队学生，先找全部学生的id，再找组队学生的id，取交集，根据id查学生信息
     * @param klassId
     * @return
     */
    public List<StudentVO> getNoTeamStudentByKlassId(BigInteger klassId) {
        List<BigInteger> allStudentId=studentMapper.getAllStudentIdByKlassId(klassId);
        List<BigInteger> teamStudentId=new ArrayList<>();//klass下所有team，team下所有studentId
        List<Team> teams=teamMapper.listTeamByKlassId(klassId);
        for(Team teamItems: teams){
            List<BigInteger> studentsId=teamMapper.getStudentIdByTeamId(teamItems.getTeamId());
            for(BigInteger i:studentsId)
                teamStudentId.add(i);
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
        return  teamMapper.listTeamByKlassId(klassId);
    }

}
