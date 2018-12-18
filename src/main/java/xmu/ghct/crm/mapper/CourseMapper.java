package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    int insertCourse(Course course);

    List<Course> listCourseByTeacherId(BigInteger teacherId);

    Course getCourseByCourseId(BigInteger courseId);

    int deleteCourseByCourseId(BigInteger courseId);


}
