package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author caiyq
 */
public class Round implements Serializable {
    private BigInteger roundId;
    private BigInteger courseId;
    /**
     * 轮次序号
     */
    private int roundSerial;
    /**
     * 本轮次成绩计算方法，0代表平均分，1代表最高分
     */
    private int presentationScoreMethod;
    private int reportScoreMethod;
    private int questionScoreMethod;
    private Double totalScore;

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public int getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(int roundSerial) {
        this.roundSerial = roundSerial;
    }

    public int getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(int presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public int getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(int reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public int getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(int questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundId=" + roundId +
                ", courseId=" + courseId +
                ", roundSerial=" + roundSerial +
                ", presentationScoreMethod=" + presentationScoreMethod +
                ", reportScoreMethod=" + reportScoreMethod +
                ", questionScoreMethod=" + questionScoreMethod +
                ", totalScore=" + totalScore +
                '}';
    }
}
