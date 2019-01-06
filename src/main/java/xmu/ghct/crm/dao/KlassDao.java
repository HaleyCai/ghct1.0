package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.KlassMapper;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * @author hzm
 */
@Component
public class KlassDao {
    @Autowired
    KlassMapper klassMapper;

    public List<Klass> listKlassByCourseId(BigInteger courseId) throws NotFoundException {
        List<Klass> klassList = klassMapper.listKlassByCourseId(courseId);
        if (klassList == null) {
            throw new NotFoundException("该课程下没有班级");
        }
        return klassList;
    }

    public int creatKlass(Klass klass) throws SQLException {
        if(klassMapper.getKlassByCourseIdAndKlassSerial(klass.getCourseId(),klass.getKlassSerial())!=null)
        {
            throw new SQLException("该班级已存在");
        }
        return klassMapper.creatKlass(klass);
    }

    public int deleteKlassByCourseId(BigInteger courseId) throws NotFoundException {
        int count=klassMapper.deleteKlassByCourseId(courseId);
        if(count<=0)
        {
            throw new NotFoundException("该课程下没有班级");
        }
        return count;
    }

    public int deleteKlassByKlassId(BigInteger klassId) throws NotFoundException {
        int count=klassMapper.deleteKlassByKlassId(klassId);
        if(count<=0)
        {
            throw new NotFoundException("未找到班级");
        }
        return count;
    }

    public Klass getKlassByKlassId(BigInteger klassId) throws NotFoundException {
        Klass klass=klassMapper.getKlassByKlassId(klassId);
        System.out.println("klass=="+klass);
        if(klass.toString().length()==0)
        {
            System.out.println("klass 是空的");
            throw new NotFoundException("未找到该班级");
        }
        return klass;
    }

    public List<BigInteger> listKlassIdBySeminarId(BigInteger seminarId) throws NotFoundException {
        List<BigInteger> list=klassMapper.listKlassIdBySeminarId(seminarId);
        if(list==null&&list.isEmpty())
        {
            throw new NotFoundException("未找到该讨论课");
        }
        return list;
    }

    public List<Integer> listStatusByKlassId(BigInteger klassId){
        return klassMapper.listStatusByKlassId(klassId);
    }

    public int getKlassSerialByKlassId(BigInteger klassId) throws NotFoundException {

        int serial=klassMapper.getKlassSerialByKlassId(klassId);
        if(serial<0)
        {
            throw new NotFoundException("未找到该班级");
        }
        return serial;
    }

    /**
     * 删除班级-轮次关联
     * @param klassId
     * @return
     */
    public int deleteKlassRoundByKlassId(BigInteger klassId) throws NotFoundException {
        int count=klassMapper.deleteKlassRoundByKlassId(klassId);
        if(count<=0)
        {
            throw new NotFoundException("未找到轮次关联");
        }
        return count;
    }

    /**
     * 删除班级-学生关系
     * @param klassId
     * @return
     */
    public int deleteKlassStudentByKlassId(BigInteger klassId) throws NotFoundException {
        int count=klassMapper.deleteKlassStudentByKlassId(klassId);
        if(count<=0)
        {
            throw new NotFoundException("未找到班级-学生关系");
        }
        return count;
    }

    /**
     * 删除班级—讨论课关系
     * @param klassId
     * @return
     */
    public int deleteKlassSeminarByKlassId(BigInteger klassId) throws NotFoundException {
        int count=klassMapper.deleteKlassSeminarByKlassId(klassId);
        if(count<=0)
        {
            throw new NotFoundException("未找到班级-讨论课关系");
        }
        return count;
    }

    /**
     * 根据klassId删除klass_team关系
     * @param klassId
     */
    public int deleteTeamWithKlass(BigInteger klassId) throws NotFoundException {
        int count=klassMapper.deleteTeamWithKlass(klassId);
        if(count<=0)
        {
            throw new NotFoundException("未找到班级-队伍关系");
        }
        return count;
    }

      /**
     * 查学生在某课程里的班级
     * @param courseId
     * @param studentId
     * @return
     */
      public BigInteger getKlassIdByCourseIdAndStudentId(BigInteger courseId,BigInteger studentId){
        return klassMapper.getKlassIdByCourseIdAndStudentId(courseId,studentId);
    }

    /**
     * 获取班级学生人数
     * @param klassId
     * @return
     */
    public Integer getStudentNumber(BigInteger klassId){
        return klassMapper.getStudentNumber(klassId);
    }

}
