package xmu.ghct.crm.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author gfj
 */
public class StudentVO implements Serializable {

    private BigInteger studentId;
    private String account;
    private String name;
    private String email;
    private BigInteger teamId;

    public BigInteger getStudentId() {
        return studentId;
    }

    public void setStudentId(BigInteger studentId) {
        this.studentId = studentId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
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
