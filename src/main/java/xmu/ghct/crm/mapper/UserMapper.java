package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;

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
}
