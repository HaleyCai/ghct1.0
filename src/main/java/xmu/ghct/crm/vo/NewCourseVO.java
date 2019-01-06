package xmu.ghct.crm.vo;

import xmu.ghct.crm.entity.Course;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
public class NewCourseVO {

    private BigInteger courseId;
    private String courseName;
    private BigInteger teacherId;
    private String introduction;
    private Double presentationPercentage;
    private Double questionPercentage;
    private Double reportPercentage;
    private String teamStartTime;
    private String teamEndTime;
    private String minMember;
    private String maxMember;
    /**
     * “与”或“或”规则
     */
    private Boolean flag;
    private List<CourseLimitVO> courseLimitVOS;
    private List<List<Course>> conflictCourseIdS;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
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

    public String getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(String teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public String getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(String teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public String getMinMember() {
        return minMember;
    }

    public void setMinMember(String minMember) {
        this.minMember = minMember;
    }

    public String getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(String maxMember) {
        this.maxMember = maxMember;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public List<CourseLimitVO> getCourseLimitVOS() {
        return courseLimitVOS;
    }

    public void setCourseLimitVOS(List<CourseLimitVO> courseLimitVOS) {
        this.courseLimitVOS = courseLimitVOS;
    }

    public List<List<Course>> getConflictCourseIdS() {
        return conflictCourseIdS;
    }

    public void setConflictCourseIdS(List<List<Course>> conflictCourseIdS) {
        this.conflictCourseIdS = conflictCourseIdS;
    }

    @Override
    public String toString() {
        return "CourseVO{" +
                ", courseName='" + courseName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", presentationPercentage=" + presentationPercentage +
                ", questionPercentage=" + questionPercentage +
                ", reportPercentage=" + reportPercentage +
                ", teamStartTime=" + teamStartTime +
                ", teamEndTime=" + teamEndTime +
                ", minMember=" + minMember +
                ", maxMember=" + maxMember +
                ", flag=" + flag +
                ", courseLimitVOS=" + courseLimitVOS +
                ", conflictCourseIdS=" + conflictCourseIdS +
                '}';
    }
}
