package top.chendawei.system.beans.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sys_menu")
public class Menu
        implements Comparable<Menu> , Serializable {
    private static final long serialVersionUID = -1L;
    private Long id;
    private String title;
    @JSONField(serialize = false)
    private Menu parent;
    private String event;
    private int weight;
    private List<Menu> subMenus;
    private boolean checked;

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

    @Transient
    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid")
    public Menu getParent() {
        return this.parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Column(name = "event", length = 100)
    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Column(name = "weight")
    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Transient
    public List<Menu> getSubMenus() {
        return this.subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Menu menu = (Menu) obj;
        return this.id == menu.getId();
    }

    public int compareTo(Menu o) {
        if (o == null) {
            return 0;
        }
        int val = this.weight - o.getWeight();
        if (val > 0) {
            return 1;
        }
        if (val < 0) {
            return -1;
        }
        return 0;
    }

    @Transient
    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<item text='" + this.title + "' id='" + this.id + "'  im0='tombs.gif' im1='tombs.gif' im2='iconSafe.gif' " + (this.checked ? "checked='1'" : "") + ">\n");
        if (this.subMenus != null) {
            for (Menu menu : this.subMenus) {
                buffer.append(menu.toXml());
            }
        }
        buffer.append("</item>");
        return buffer.toString();
    }
}
