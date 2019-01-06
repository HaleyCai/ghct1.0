package xmu.ghct.crm.vo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author gfj
 */
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
    private Integer minMember;
    private Integer maxMember;
    private BigInteger memberLimitId;
    private Boolean flag;
    private List<CourseLimitVO> courseLimitVOS;
    private List<List<BigInteger>> conflictCourseIdS;

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

    public Integer getMinMember() {
        return minMember;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public BigInteger getMemberLimitId() {
        return memberLimitId;
    }

    public void setMemberLimitId(BigInteger memberLimitId) {
        this.memberLimitId = memberLimitId;
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

    public List<List<BigInteger>> getConflictCourseIdS() {
        return conflictCourseIdS;
    }

    public void setConflictCourseIdS(List<List<BigInteger>> conflictCourseIdS) {
        this.conflictCourseIdS = conflictCourseIdS;
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
                ", flag=" + flag +
                ", courseLimitVOS=" + courseLimitVOS +
                ", conflictCourseIdS=" + conflictCourseIdS +
                '}';
    }
}
