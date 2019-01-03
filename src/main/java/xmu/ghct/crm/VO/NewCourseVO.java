package xmu.ghct.crm.VO;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class NewCourseVO {

    private String courseName;
    private String introduction;
    private Double presentationPercentage;
    private Double questionPercentage;
    private Double reportPercentage;
    private String teamStartTime;
    private String teamEndTime;
    private int minMember;
    private int maxMember;
    private boolean flag;    //“与”或“或”规则
    private List<CourseLimitVO> courseLimitVOS;
    private List<BigInteger> conflictCourseIdS;


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setCourseLimitVOS(List<CourseLimitVO> courseLimitVOS) {
        this.courseLimitVOS = courseLimitVOS;
    }

    public void setConflictCourseIdS(List<BigInteger> conflictCourseIdS) {
        this.conflictCourseIdS = conflictCourseIdS;
    }

    public List<CourseLimitVO> getCourseLimitVOS() {
        return courseLimitVOS;
    }

    public List<BigInteger> getConflictCourseIdS() {
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

    public void setMinMember(int minMember) {
        this.minMember = minMember;
    }

    public void setMaxMember(int maxMember) {
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

    public int getMinMember() {
        return minMember;
    }

    public int getMaxMember() {
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