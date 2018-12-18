package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.ScoreDao;
import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    ScoreDao scoreDao;
    public List<Score> listScoreByCourseId(BigInteger courseId){
        return scoreDao.listScoreByCourseId(courseId);
    }
}
