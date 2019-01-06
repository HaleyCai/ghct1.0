package xmu.ghct.crm.VO;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentVO implements Serializable {

    private BigInteger studentId;
    private String account;
    private String name;
    private String email;
    private BigInteger teamId;

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public BigInteger getStudentId() {
        return studentId;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "StudentVO{" +
                "studentId=" + studentId +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
