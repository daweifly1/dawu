package net.swa.system.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sys_dict")
public class Dict
        implements Serializable {
    private static final long serialVersionUID = -9201118331824805328L;
    private long id;
    private String title;
    private String code;
    private String key;
    private String value;
    private int dictPaixu;
    private List<Dict> subDict;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "native")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "dict_key")
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "dict_value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "dict_type")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "dict_paixu")
    public int getDictPaixu() {
        return this.dictPaixu;
    }

    public void setDictPaixu(int dictPaixu) {
        this.dictPaixu = dictPaixu;
    }

    @Transient
    public List<Dict> getSubDict() {
        return this.subDict;
    }

    public void setSubDict(List<Dict> subDict) {
        this.subDict = subDict;
    }

    @Transient
    public String toXml() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<item text='" + this.key + "' id='" + this.id + "'  im0='city.png' im1='county.png' im2='city.png'  >");
        if (this.subDict != null) {
            for (Dict dict : this.subDict) {
                buffer.append(dict.toXml());
            }
        }
        buffer.append("</item>");
        return buffer.toString();
    }

    @Column(name = "type_code")
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
