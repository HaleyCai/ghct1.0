package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Round implements Serializable {
    private BigInteger id;
    private BigInteger courseId;
    private int roundSerial;//轮次序号
    private int presentationScoreMethod;//本轮次成绩计算方法
    private int reportScoreMethod;
    private int QuestionScoreMethod;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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
        return QuestionScoreMethod;
    }

    public void setQuestionScoreMethod(int questionScoreMethod) {
        QuestionScoreMethod = questionScoreMethod;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", roundSerial=" + roundSerial +
                ", presentationScoreMethod=" + presentationScoreMethod +
                ", reportScoreMethod=" + reportScoreMethod +
                ", QuestionScoreMethod=" + QuestionScoreMethod +
                '}';
    }
}
