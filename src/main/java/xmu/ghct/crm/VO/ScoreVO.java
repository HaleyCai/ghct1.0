package xmu.ghct.crm.VO;
import java.math.BigInteger;
import java.util.List;

public class ScoreVO {

    private BigInteger roundId;
    private int roundSerial;
    private BigInteger teamId;
    private int teamSerial;
    private int klassSerial;
    private double presentationScore;
    private double questionScore;
    private double reportScore;
    private double totalScore;
    private List<SeminarScoreVO> scoreList;

    public BigInteger getRoundId() {
        return roundId;
    }

    public void setRoundId(BigInteger roundId) {
        this.roundId = roundId;
    }

    public int getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(int roundSerial) {
        this.roundSerial = roundSerial;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public int getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(int teamSerial) {
        this.teamSerial = teamSerial;
    }

    public double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(double questionScore) {
        this.questionScore = questionScore;
    }

    public double getReportScore() {
        return reportScore;
    }

    public void setReportScore(double reportScore) {
        this.reportScore = reportScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public List<SeminarScoreVO> getScoreList() {
       return scoreList;
   }

   public void setScoreList(List<SeminarScoreVO> scoreList) {
       this.scoreList = scoreList;
   }

    public int getKlassSerial() {
        return klassSerial;
    }

    public void setKlassSerial(int klassSerial) {
        this.klassSerial = klassSerial;
    }

    // }


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
                '}';
    }

}
