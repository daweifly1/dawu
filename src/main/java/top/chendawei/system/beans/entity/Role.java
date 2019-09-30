package top.chendawei.system.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sys_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -1L;
    private Long id;
    private String name;
    private String description;
    private List<Menu> menus;
    private List<User> users;

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

    @Column(name = "name", length = 100, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_menu", joinColumns = {@javax.persistence.JoinColumn(name = "roleId")}, inverseJoinColumns = {@javax.persistence.JoinColumn(name = "menuid")})
    public List<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Column(name = "description", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        if (this.id == null) {
            this.id = Long.valueOf(0L);
        }
        buffer.append("<item text='" + this.name + "' id='" + this.id + "' im0='user.png' im1='user.png' im2='user.png'  >");
        if (this.users != null) {
            for (User u : this.users) {
                buffer.append(u.toXml());
            }
        }
        buffer.append("</item>");
        return buffer.toString();
    }

    @Transient
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
