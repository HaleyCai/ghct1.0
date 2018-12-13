package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    void insertCourse(Course course);

    List<Course> listCourseByTeacherId(BigInteger teacherId);

    List<Course> getCourseByCourseId(BigInteger courseId);

    void deleteCourseByCourseId(BigInteger courseId);

    List<Score> listScoreByCourseId(BigInteger courseId);

    List<Klass> listKlassByCoursId(BigInteger courseId);

    List<Team> getTeamMessageByCourseId(BigInteger courseId);

    List<User> getNoTeamStudentByCourseId(BigInteger courseId);

    void deleteClassByCourseIdAndClassId(BigInteger courseId,BigInteger classId);


}