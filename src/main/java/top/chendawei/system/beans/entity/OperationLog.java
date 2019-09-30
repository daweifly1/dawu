package top.chendawei.system.beans.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_log")
public class OperationLog {
    @GenericGenerator(name = "generator", strategy = "native")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    private String userid;
    private String uri;
    private String descript;
    private String ip;
    private String updateDate;
    private Integer status;

}
