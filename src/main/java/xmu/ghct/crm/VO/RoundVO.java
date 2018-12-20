package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class RoundVO implements Serializable {
    private BigInteger roundId;
    private int roundSerial;//轮次序号
    private String presentationScoreMethod;//本轮次成绩计算方法，0转为“平均分”，1转为“最高分”
    private String reportScoreMethod;
    private String questionScoreMethod;

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public int getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(int roundSerial) {
        this.roundSerial = roundSerial;
    }

    public String getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(String presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public String getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(String reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public String getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(String questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }

    @Override
    public String toString() {
        return "RoundVO{" +
                "roundId=" + roundId +
                ", roundSerial=" + roundSerial +
                ", presentationScoreMethod='" + presentationScoreMethod + '\'' +
                ", reportScoreMethod='" + reportScoreMethod + '\'' +
                ", questionScoreMethod='" + questionScoreMethod + '\'' +
                '}';
    }
}
