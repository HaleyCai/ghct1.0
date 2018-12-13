package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    void insertCourse(Course course);

    List<Course> listCourseByTeacherId(BigInteger teacherId);

    void deleteCourseByCourseId(BigInteger courseId);


}
