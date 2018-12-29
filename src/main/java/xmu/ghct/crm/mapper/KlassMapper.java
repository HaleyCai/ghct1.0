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
     * 删除某课程下的所有班级
     * @author hzm
     * @param courseId
     * @return
     */
    int deleteKlassByCourseId(BigInteger courseId);

    /**
     * 删除某一个班级
     * @param klassId
     * @return
     */
    int deleteKlassByKlassId(BigInteger klassId);

    /**
     * 根据klassId获得班级信息
     * @param klassId
     * @return
     */
    Klass getKlassByKlassId(BigInteger klassId);


    /**
     * 根据seminarId获得讨论课下的所有班级id
     * @param seminarId
     * @return
     */
   List<BigInteger> listKlassIdBySeminarId(BigInteger seminarId);

    /**
     * @cyq
     * 根据courseId和klassSerial获取klassId
     * @param grade
     * @param klassSerial
     * @return
     */
   Klass getKlassByGradeAndKlassSerial(int grade,int klassSerial);

    /**
     * @author hzm
     * 根据班级id获取所有班级讨论课的status
     * @param klassId
     * @return
     */
   List<Integer> listStatusByKlassId(BigInteger klassId);
}
