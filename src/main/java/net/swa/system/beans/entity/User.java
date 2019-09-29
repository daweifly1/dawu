package net.swa.system.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_user")
public class User {
    private long id;
    private String loginName;
    private String password;
    private String realName;
    private Role role;
    private int status;
    private int weight;
    private String regDate;
    private String email;
    private String loginDate;
    private Short userType;

    @GenericGenerator(name = "generator", strategy = "native")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "userid", unique = true, length = 100)
    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "pwd", length = 100)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "realName")
    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Transient
    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<item text='" + this.realName + "' id='" + this.id + "'  im0='user.png' im1='user.png' im2='user.png'  >");
        buffer.append("</item>");
        return buffer.toString();
    }

    public String getRegDate() {
        return this.regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Short getUserType() {
        return this.userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }
}
