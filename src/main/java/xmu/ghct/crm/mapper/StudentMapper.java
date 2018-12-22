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
     * ****有问题，没完成！！！此处@闽儿
     * 根据课程id,查询所有未组队学生
     * @return
     */
    List<User> getNoTeamStudentByCourseId();


    /**
     * 根据account设置student新密码
     * @param account
     * @return
     */
    int setStudentPasswordByAccount(@Param("account") String account, @Param("password") String password);

    /**
     * 根据account设置student新邮箱
     * @param account
     * @return
     */
    int setStudentEmailByAccount(@Param("account") String account, @Param("email") String email);

    /**
     * 根据account激活student
     * @param account
     * @return
     */
    int setStudentActiveByAccount(@Param("account") String account);

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
    int resetStudentPasswordByStudentId(@Param("id") BigInteger studentId,@Param("password") String studentPassword);

    /**
     * 管理员按ID删除某一学生
     * @return
     */
    int deleteStudentByStudentId(@Param("id") BigInteger studentId);
}

    /**
     * 管理员重置某一学生的密码
     * @return
     */
    int resetStudentPasswordByStudentId(BigInteger studentId,String studentPassword);

    /**
     * 管理员按ID删除某一学生
     * @return
     */
    int deleteStudentByStudentId(BigInteger studentId);
}