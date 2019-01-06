package xmu.ghct.crm.vo;

import java.util.List;

/**
 * @author caiyq
 */
public class RoundEnrollListVO {
    private List<RoundEnrollVO> roundEnrollList;

    public List<RoundEnrollVO> getRoundEnrollList() {
        return roundEnrollList;
    }

    public void setRoundEnrollList(List<RoundEnrollVO> roundEnrollList) {
        this.roundEnrollList = roundEnrollList;
    }

    @Override
    public String toString() {
        return "RoundEnrollListVO{" +
                "roundEnrollList=" + roundEnrollList +
                '}';
    }
}
