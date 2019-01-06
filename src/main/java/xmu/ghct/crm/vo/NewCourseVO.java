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
    //“与”或“或”规则
    private Boolean flag;
    private List<CourseLimitVO> courseLimitVOS;
    private List<List<Course>> conflictCourseIdS;


    public void setTeacherId(BigInteger teacherId) {
        this.teacherId = teacherId;
    }

    public BigInteger getTeacherId() {
        return teacherId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setCourseLimitVOS(List<CourseLimitVO> courseLimitVOS) {
        this.courseLimitVOS = courseLimitVOS;
    }

    public void setConflictCourseIdS(List<List<Course>> conflictCourseIdS) {
        this.conflictCourseIdS = conflictCourseIdS;
    }

    public List<CourseLimitVO> getCourseLimitVOS() {
        return courseLimitVOS;
    }

    public List<List<Course>> getConflictCourseIdS() {
        return conflictCourseIdS;
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

    public void setTeamStartTime(String teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public void setTeamEndTime(String teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public void setMinMember(String minMember) {
        this.minMember = minMember;
    }

    public void setMaxMember(String maxMember) {
        this.maxMember = maxMember;
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

    public String getTeamStartTime() {
        return teamStartTime;
    }

    public String getTeamEndTime() {
        return teamEndTime;
    }

    public String getMinMember() {
        return minMember;
    }

    public String getMaxMember() {
        return maxMember;
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
