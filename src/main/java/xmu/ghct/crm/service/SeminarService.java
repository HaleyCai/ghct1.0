package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;

@Service
public class SeminarService {
    @Autowired
    private RoundDao roundDao;

    public List<Seminar> getSeminarByRoundId(BigInteger roundId)
    {
        return roundDao.getSeminarByRoundId(roundId);
    }
}
