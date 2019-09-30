package top.chendawei.index.beans.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "file_apk_version")
public class ApkVersion {
    private Long id;
    private String name;
    private String verCode;
    private String verName;
    private String packageName;
    private String verPath;
    private String detail;
    private boolean admin;
    private List<String> feature;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerCode() {
        return this.verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getVerName() {
        return this.verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVerPath() {
        return this.verPath;
    }

    public void setVerPath(String verPath) {
        this.verPath = verPath;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Transient
    public List<String> getFeature() {
        return this.feature;
    }

    public void setFeature(List<String> feature) {
        this.feature = feature;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
