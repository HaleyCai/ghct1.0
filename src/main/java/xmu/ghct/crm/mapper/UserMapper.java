package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.User;

/**
 * @author caiyq
 */

@Mapper     //声明是一个Mapper
@Repository
public interface UserMapper {
    /**
     * 根据account查询teacher表信息
     * @param account
     * @return
     */
    User getTeacherByAccount(@Param("account") String account);

    /**
     * 根据account查询student表信息
     * @param account
     * @return
     */
    User getStudentByAccount(@Param("account") String account);

    /**
     * 根据account设置teacher新密码
     * @param account
     * @return
     */
    int setTeacherPasswordByAccount(@Param("account") String account, @Param("password") String password);

    /**
     * 根据account设置student新密码
     * @param account
     * @return
     */
    int setStudentPasswordByAccount(@Param("account") String account, @Param("password") String password);

    /**
     * 根据account设置teacher新邮箱
     * @param account
     * @return
     */
    int setTeacherEmailByAccount(@Param("account") String account, @Param("email") String email);

    /**
     * 根据account设置student新邮箱
     * @param account
     * @return
     */
    int setStudentEmailByAccount(@Param("account") String account, @Param("email") String email);

    /**
     * 根据account激活teacher
     * @param account
     * @return
     */
    int setTeacherActiveByAccount(@Param("account") String account);

    /**
     * 根据account激活student
     * @param account
     * @return
     */
    int setStudentActiveByAccount(@Param("account") String account);
}
