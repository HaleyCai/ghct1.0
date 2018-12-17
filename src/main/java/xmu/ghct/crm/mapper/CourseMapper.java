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

    Course getCourseByCourseId(BigInteger courseId);

    void deleteCourseByCourseId(BigInteger courseId);

    List<Score> listScoreByCourseId(BigInteger courseId);

    List<Klass> listKlassByCourseId(BigInteger courseId);

    List<Team> getTeamInfoByCourseId(BigInteger courseId);

    List<User> getNoTeamStudentByCourseId(BigInteger courseId);

    void deleteClassByCourseIdAndClassId(BigInteger courseId,BigInteger classId);

    void createKlass(BigInteger courseId,Klass klass);

    List<Share> getShareMessageByCourseId(BigInteger courseId);

    void deleteShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId);

    void launchShareRequest(BigInteger courseId, Share share);
}
