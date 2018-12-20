package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CreatCourseVO;
import xmu.ghct.crm.entity.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface CourseMapper {

    /**
     * 创建课程
     * @author hzm
     * @param creatCourseVO
     * @return
     */
    int insertCourse(CreatCourseVO creatCourseVO);

    /**
     * 根据teacherId获得课程信息
     * @author hzm
     * @param teacherId
     * @return
     */
    List<Course> listCourseByTeacherId(BigInteger teacherId);

    /**
     * 根据courseId查找课程
     * @author hzm
     * @param courseId
     * @return
     */
    Course getCourseByCourseId(BigInteger courseId);

    /**
     * 根据courseId删除课程
     * @author hzm
     * @param courseId
     * @return
     */
    int deleteCourseByCourseId(BigInteger courseId);


}
