package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Klass;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface KlassMapper {

    /**
     * 根据courseId获得班级信息
     * @author hzm
     * @param courseId
     * @return
     */
    List<Klass> listKlassByCourseId(BigInteger courseId);

    /**
     * 创建班级
     * @author hzm
     * @param klass
     * @return
     */
    int creatKlass(Klass klass);

    /**
     * 根据courseId和klassId删除班级
     * @author hzm
     * @param courseId
     * @return
     */
    int deleteKlassByCourseIdAndKlassId(BigInteger courseId);
}
