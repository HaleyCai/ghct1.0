package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Param;
import xmu.ghct.crm.entity.Course;

public class courseMapper {

    void insertCourseByUserId(@Param("course") Course course);


}
