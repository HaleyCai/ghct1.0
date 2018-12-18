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

    List<Klass> listKlassByCourseId(BigInteger courseId);

    int creatKlass(Klass klass);

    int deleteKlassByCourseIdAndKlassId(BigInteger courseId,BigInteger klassId);
}
