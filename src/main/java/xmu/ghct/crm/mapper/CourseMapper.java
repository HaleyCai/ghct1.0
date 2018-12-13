package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Team;
import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    void insertCourse(Course course);

    List<Course> listCourseByTeacherId(BigInteger teacherId);

    List<Course> getCourseByCourseId(BigInteger courseId);

    void deleteCourseByCourseId(BigInteger courseId);

    List<Team> getTeamMessageByCourseId(BigInteger courseId);
}
