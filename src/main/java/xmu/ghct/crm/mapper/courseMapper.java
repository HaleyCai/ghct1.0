package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Param;
import xmu.ghct.crm.entity.Course;

import java.math.BigInteger;
import java.util.List;

public class courseMapper {

    void insertCourseByUserId(@Param("course") Course course);

    List<Course> listCourseByTeacherId(@Param("teacherId")BigInteger teacherId);


}
