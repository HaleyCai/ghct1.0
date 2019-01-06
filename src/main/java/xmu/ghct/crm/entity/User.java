package xmu.ghct.crm.entity;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author caiyq
 */
public class User implements Serializable {
    private BigInteger id;
    private String account;
    private String password;
    private String name;
    private String email;
    private BigInteger teamId;
    /**
     * 0是学生，1是教师
     */
    private int type;
    /**
     * 0是未激活，1是激活
     */
    private int active;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", teamId=" + teamId +
                ", type=" + type +
                ", active=" + active +
                '}';
    }
}
