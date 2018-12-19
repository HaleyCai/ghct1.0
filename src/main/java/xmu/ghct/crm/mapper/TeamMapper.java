package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface TeamMapper {

    List<BigInteger> getTeamIdByCourseId(BigInteger courseId);

    List<Team> getTeamInfoByCourseId(BigInteger courseId);

    List<User> getNoTeamStudentByCourseId(BigInteger courseId);
}
