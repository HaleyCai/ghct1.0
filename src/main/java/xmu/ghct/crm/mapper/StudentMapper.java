package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */

@Mapper     //声明是一个Mapper
@Component
public interface StudentMapper {

    /**
     * 根据account获取学生信息
     * @param account
     * @return
     */
    User getStudentByAccount(@Param("account") String account);

    /**
     * 根据学生id获取学生信息
     * @param studentId
     * @return
     */
    User getStudentByStudentId(@Param("studentId") BigInteger studentId);

    /**
     * 根据id设置student新密码
     * @param id
     * @return
     */
    int setStudentPasswordById(@Param("id") BigInteger id, @Param("password") String password);

    /**
     * 根据id设置student新邮箱
     * @param id
     * @return
     */
    int setStudentEmailById(@Param("id") BigInteger id, @Param("email") String email);

    /**
     * 根据id激活student
     * @param id
     * @return
     */
    int setStudentActiveById(@Param("id") BigInteger id);

    /**
     *管理员获得所有学生信息
     * @return
     */
    List<User> getAllStudent();

    /**
     * 管理员根据学生姓名获得学生信息
     * @return
     */
    User getStudentByStudentName(@Param("name") String studentName);

    /**
     * 管理员修改某一学生的信息（姓名，账号，邮箱）
     * @return
     */
    int modifyStudentByStudentId(@Param("id") BigInteger studentId, @Param("name") String studentName,
                                 @Param("account") String studentAccount,@Param("email") String studentEmail);

    /**
     * 管理员重置某一学生的密码
     * @return
     */
    int resetStudentPasswordByStudentId(@Param("id") BigInteger studentId);

    /**
     * 管理员按ID删除某一学生
     * @return
     */
    int deleteStudentByStudentId(@Param("id") BigInteger studentId);

    /**
     * 获取课程下所有学生的id
     * @param courseId
     * @return
     */
    List<BigInteger> getAllStudentIdByCourseId(BigInteger courseId);

    /**
     * @author hzm
     * 插入学生用户
     * @param user
     * @return
     */
    int insertStudent(User user);


    /**
     * 创建klass_student信息
     * @param studentId
     * @param klassId
     * @param courseId
     * @return
     */
    int insertKlassStudent(BigInteger studentId,BigInteger klassId,BigInteger courseId);

    /**
     * 查看klass下是否有该学生
     * @param studentId
     * @param klassId
     * @return
     */
    BigInteger getStudentIdByStudentIdAndKlassId(BigInteger studentId,BigInteger klassId);


    /**
     * 获取班级下所有学生ID
     * @param klassId
     * @return
     */
    List<BigInteger> listStudentByKlassId(BigInteger klassId);

    /**
     * 删除班级下所有学生关联
     * @param klassId
     * @return
     */
    int deleteKlassStudentByKlassId(BigInteger klassId);
}
