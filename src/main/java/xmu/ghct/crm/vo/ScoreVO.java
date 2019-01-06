package xmu.ghct.crm.vo;
import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
public class ScoreVO {

    private BigInteger roundId;
    private Integer roundSerial;
    private BigInteger teamId;
    private Integer teamSerial;
    private Integer klassSerial;
    private Double presentationScore;
    private Double questionScore;
    private Double reportScore;
    private Double totalScore;
    private List<SeminarScoreVO> scoreList;

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

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(Integer teamSerial) {
        this.teamSerial = teamSerial;
    }

    public Integer getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(Integer klassSerial) {
        this.klassSerial = klassSerial;
    }

    public Double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }

    public Double getReportScore() {
        return reportScore;
    }

    public void setReportScore(Double reportScore) {
        this.reportScore = reportScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public List<SeminarScoreVO> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<SeminarScoreVO> scoreList) {
        this.scoreList = scoreList;
    }

    @Override
    public String toString() {
        return "ScoreVO{" +
                "roundId=" + roundId +
                ", roundSerial=" + roundSerial +
                ", teamId=" + teamId +
                ", teamSerial=" + teamSerial +
                ", klassSerial=" + klassSerial +
                ", presentationScore=" + presentationScore +
                ", questionScore=" + questionScore +
                ", reportScore=" + reportScore +
                ", totalScore=" + totalScore +
                ", scoreList=" + scoreList +
                '}';
    }

}
