package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Share;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface ShareMapper {

    List<Share> listTeamShareMessageByCourseId(BigInteger courseId);

    int deleteTeamShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId);

    int launchTeamShareRequest(Share share);

    List<Share> listSeminarShareMessageByCourseId(BigInteger courseId);

    int deleteSeminarShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId);

    int launchSeminarShareRequest(Share share);
}
