package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;

@Mapper
@Component
public interface CourseMapper {

    void insertCourse(@Param("course") Course course);


}
