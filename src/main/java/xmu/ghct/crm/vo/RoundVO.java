package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */
public class RoundVO implements Serializable {
    private BigInteger courseId;
    private BigInteger roundId;
    /**
     * 轮次序号
     */
    private Integer roundSerial;
    /**
     * 本轮次成绩计算方法，0转为“平均分”，1转为“最高分”
     */
    private String presentationScoreMethod;
    private String reportScoreMethod;
    private String questionScoreMethod;
    /**
     * 各班的最大报名次数
     */
    private List<RoundEnrollVO> enrollNum;

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public Integer getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Integer roundSerial) {
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

    public List<RoundEnrollVO> getEnrollNum() {
        return enrollNum;
    }

    public void setEnrollNum(List<RoundEnrollVO> enrollNum) {
        this.enrollNum = enrollNum;
    }

    @Override
    public String toString() {
        return "RoundVO{" +
                "courseId=" + courseId +
                ", roundId=" + roundId +
                ", roundSerial=" + roundSerial +
                ", presentationScoreMethod='" + presentationScoreMethod + '\'' +
                ", reportScoreMethod='" + reportScoreMethod + '\'' +
                ", questionScoreMethod='" + questionScoreMethod + '\'' +
                ", enrollNum=" + enrollNum +
                '}';
    }
}
