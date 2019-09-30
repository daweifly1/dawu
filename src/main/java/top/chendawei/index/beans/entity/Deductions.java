package top.chendawei.index.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gym_deductions")
public class Deductions {
    private Long id;
    private String username;
    private String exeUser;
    private String type;
    private Double money;
    private String exeCname;
    private String createTime;
    private String completeTime;
    private Integer status;
    private boolean remind;

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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExeUser() {
        return this.exeUser;
    }

    public void setExeUser(String exeUser) {
        this.exeUser = exeUser;
    }

    public String getExeCname() {
        return this.exeCname;
    }

    public void setExeCname(String exeCname) {
        this.exeCname = exeCname;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompleteTime() {
        return this.completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public boolean getRemind() {
        return this.remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }
}
