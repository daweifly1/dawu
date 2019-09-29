package net.swa.system.beans.entity;

import lombok.Data;
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

@Data
@Entity
@Table(name = "sys_user")
public class User {
    @GenericGenerator(name = "generator", strategy = "native")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    @Column(name = "userid", unique = true, length = 100)
    private String loginName;

    @Column(name = "pwd", length = 100)
    private String password;
    @Column(name = "realName")
    private String realName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleid")
    private Role role;
    @Column(name = "status")
    private int status;
    private int weight;
    private String regDate;
    private String email;
    private String loginDate;

    private Short userType;


    @Transient
    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<item text='" + this.realName + "' id='" + this.id + "'  im0='user.png' im1='user.png' im2='user.png'  >");
        buffer.append("</item>");
        return buffer.toString();
    }


}
