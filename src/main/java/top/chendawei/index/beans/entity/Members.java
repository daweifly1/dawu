package top.chendawei.index.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gym_members")
public class Members {
    private Long id;
    private String uuid;
    private String loginName;
    private String password;
    private String mac;
    private String roleId;
    private String createTime;
    private String loginTime;
    private Boolean locked;
    private String lockedTime;
    private Integer errorTimes;
    private Boolean firstLogin;
    private boolean passed;
    private Integer qx1;
    private Integer qx2;
    private Integer qx3;
    private Integer qx4;
    private Integer qx5;

    @GenericGenerator(name = "generator", strategy = "native")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedTime() {
        return this.lockedTime;
    }

    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }

    public Integer getErrorTimes() {
        return this.errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getFirstLogin() {
        return this.firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Integer getQx1() {
        return this.qx1;
    }

    public void setQx1(Integer qx1) {
        this.qx1 = qx1;
    }

    public Integer getQx2() {
        return this.qx2;
    }

    public void setQx2(Integer qx2) {
        this.qx2 = qx2;
    }

    public Integer getQx3() {
        return this.qx3;
    }

    public void setQx3(Integer qx3) {
        this.qx3 = qx3;
    }

    public Integer getQx4() {
        return this.qx4;
    }

    public void setQx4(Integer qx4) {
        this.qx4 = qx4;
    }

    public Integer getQx5() {
        return this.qx5;
    }

    public void setQx5(Integer qx5) {
        this.qx5 = qx5;
    }

    public boolean isPassed() {
        return this.passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
