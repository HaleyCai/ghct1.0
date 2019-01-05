package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Klass;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
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


    /**
     * @author hzm
     * 根据klassId获取课次序号
     * @param klassId
     * @return
     */
   Integer getKlassSerialByKlassId(BigInteger klassId);


    /**
     * 删除班级-轮次关联
     * @param klassId
     * @return
     */
   int deleteKlassRoundByKlassId(BigInteger klassId);

    /**
     * 删除班级-学生关系
     * @param klassId
     * @return
     */
   int deleteKlassStudentByKlassId(BigInteger klassId);

    /**
     * 删除班级—讨论课关系
     * @param klassId
     * @return
     */
   int deleteKlassSeminarByKlassId(BigInteger klassId);

    /**
     * 根据klassId删除klass_team关系
     * @param klassId
     */
    int deleteTeamWithKlass(BigInteger klassId);

    /**
     * 查找班级下是否有该班级
     * @param courseId
     * @param klassSerial
     * @return
     */
    Klass getKlassByCourseIdAndKlassSerial(BigInteger courseId,int klassSerial);

    /**
     * 查学生在某课程里的班级
     * @param courseId
     * @param studentId
     * @return
     */
    BigInteger getKlassIdByCourseIdAndStudentId(BigInteger courseId,BigInteger studentId);

    /**
     * 获取班级学生人数
     * @param klassId
     * @return
     */
    Integer getStudentNumber(BigInteger klassId);
}
