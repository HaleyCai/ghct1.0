package xmu.ghct.crm.VO;

import java.math.BigInteger;
import java.util.Date;

public class CourseVO {

    private BigInteger courseId;
    private BigInteger teacherId;
    private String courseName;
    private String introduction;
    private Double presentationPercentage;
    private Double questionPercentage;
    private Double reportPercentage;
    private Date teamStartTime;
    private Date teamEndTime;
    private int minMember;
    private int maxMember;


    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPresentationPercentage(Double presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public void setQuestionPercentage(Double questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public void setReportPercentage(Double reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public void setTeamStartTime(Date teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public void setTeamEndTime(Date teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public void setMinMember(int minMember) {
        this.minMember = minMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }



    public BigInteger getCourseId() {
        return courseId;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Double getPresentationPercentage() {
        return presentationPercentage;
    }

    public Double getQuestionPercentage() {
        return questionPercentage;
    }

    public Double getReportPercentage() {
        return reportPercentage;
    }

    public Date getTeamStartTime() {
        return teamStartTime;
    }

    public Date getTeamEndTime() {
        return teamEndTime;
    }

    public int getMinMember() {
        return minMember;
    }

    public int getMaxMember() {
        return maxMember;
    }

    @Override
    public String toString() {
        return "CourseVO{" +
                "courseId=" + courseId +
                ", teacherId=" + teacherId +
                ", courseName='" + courseName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime=" + teamStartTime +
                ", teamEndTime=" + teamEndTime +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                '}';
    }
}
