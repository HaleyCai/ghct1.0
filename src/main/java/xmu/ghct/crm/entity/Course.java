package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Course implements Serializable {
    private BigInteger courseId;
    private BigInteger teacherId;
    private String courseName;
    private String introduction;
    private Double presentationPercentage;
    private Double questionPercentage;
    private Double reportPercentage;
    private Date teamStartTime;
    private Date teamEndTime;

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Double getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Double presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Double getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Double questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Double getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Double reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public Date getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(Date teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public Date getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(Date teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    @Override
    public String toString() {
        return "Course{" +
                "teacherId=" + teacherId +
                ", courseName='" + courseName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime=" + teamStartTime +
                ", teamEndTime=" + teamEndTime +
                '}';
    }
}
