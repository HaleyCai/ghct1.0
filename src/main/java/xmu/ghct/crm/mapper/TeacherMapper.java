package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */

@Mapper     //声明是一个Mapper
@Repository
public interface TeacherMapper {
    /**
     * 根据account查询teacher表信息
     * @param account
     * @return
     */
    User getTeacherByAccount(@Param("account") String account);

    /**
     * 根据account设置teacher新密码
     * @param account
     * @return
     */
    int setTeacherPasswordByAccount(@Param("account") String account, @Param("password") String password);

    /**
     * 根据account设置teacher新邮箱
     * @param account
     * @return
     */
    int setTeacherEmailByAccount(@Param("account") String account, @Param("email") String email);

    /**
     * 根据account激活teacher
     * @param account
     * @return
     */
    int setTeacherActiveByAccount(@Param("account") String account);

    /**
     * 管理员创建一个教师
     */
    int createTeacher(User teacher);

    /**
     *管理员获得所有教师信息
     * @return
     */
    List<User> getAllTeacher();

    /**
     * 管理员根据教师姓名获得教师信息
     * @return
     */
    User getTeacherByTeacherName(String teacherName);

    /**
     * 管理员修改某一教师的信息（姓名，账号，邮箱）
     * @return
     */
    int modifyTeacherByTeacherId(BigInteger teacherId, String teacherName,
                                 String teacherAccount, String teacherEmail);

    /**
     * 管理员重置某一教师的密码
     * @return
     */
    int resetTeacherPasswordByTeacherId(BigInteger teacherId,String teacherPassword);

    /**
     * 管理员按ID删除某一教师
     * @return
     */
    int deleteTeacherByTeacherId(BigInteger teacherId);
}
