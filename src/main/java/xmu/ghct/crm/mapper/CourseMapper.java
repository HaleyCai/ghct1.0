package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.vo.CourseStudentVO;
import xmu.ghct.crm.vo.CourseVO;
import xmu.ghct.crm.vo.StudentCourseVO;
import xmu.ghct.crm.entity.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
@Mapper
@Repository
public interface CourseMapper {

    /**
     * 创建课程
     * @author hzm
     * @param courseVO
     * @return
     */
    int insertCourse(CourseVO courseVO);

    /**
     * 获取某课程名的课程ID
     * @param courseName
     * @param teacherId
     * @return
     */
    BigInteger getCourseIdByCourseNameAndTeacherId(String courseName,BigInteger teacherId);

    /**
     * 根据teacherId获得课程信息
     * @author hzm
     * @param teacherId
     * @return
     */
    List<Course> listCourseByTeacherId(BigInteger teacherId);

    /**
     * 根据studentId获得课程和班级id
     * @param studentId
     * @return
     */
    List<CourseStudentVO> listCourseByStudentId(BigInteger studentId);

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

    /**
     * 根据teamId获得courseId
     * @param teamId
     * @return
     */
    BigInteger getCourseIdByTeamId(BigInteger teamId);

    /**
     * 根据klassId获取课程ID
     * @param klassId
     * @return
     */
    BigInteger getCourseIdByKlassId(BigInteger klassId);

    /**
     * 根据teacherId获取所有课程id
     * @param teacherId
     * @return
     */
    List<BigInteger> listCourseIdByTeacherId(BigInteger teacherId);

    /**
     * 根据courseId获取所有班级的ID
     * @param courseId
     * @return
     */
    List<BigInteger> listKlassIdByCourseId(BigInteger courseId);

    /**
     * 获取学生所有的
     * @param studentId
     * @return
     */
    List<BigInteger> listCourseIdByStudentId(BigInteger studentId);

    /**
     * 获取学生相应班级ID和课程ID
     * @param studentId
     * @return
     */
    List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId);

    /**
     * 获得所有课程的信息
     * @return
     */
    List<CourseVO> getAllCourse();

    /**
     * 获取教师的全部课程
     * @param teacherId
     * @return
     */
    List<Course> getCourseByTeacherId(BigInteger teacherId);

    /**
     * 修改从课程course表的team_main字段为主课程Id
     * @param subCourseId
     * @param mainCourseId
     * @return
     */
    int updateMainTeamByCourseId(BigInteger subCourseId,BigInteger mainCourseId);

    /**
     * 修改从课程course表的seminar_main字段为主课程Id
     * @param subCourseId
     * @param mainCourseId
     * @return
     */
    int updateMainSemianrByCourseId(BigInteger subCourseId,BigInteger mainCourseId);
}
