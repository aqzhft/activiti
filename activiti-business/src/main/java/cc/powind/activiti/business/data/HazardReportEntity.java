package cc.powind.activiti.business.data;

import cc.powind.activiti.business.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@javax.persistence.Table(name = "business_hazard_report")
@org.hibernate.annotations.Table(appliesTo = "business_hazard_report", comment = "隐患报告")
public class HazardReportEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int comment 'ID'")
    private Long id;

    @Column(name = "hazard_key", columnDefinition = "varchar(50) comment '报告编号'")
    private String hazardKey;

    @Column(name = "name", columnDefinition = "varchar(128) comment '报告名称'")
    private String name;

    @Column(name = "type", columnDefinition = "varchar(50) comment '类别'")
    private String type;

    @Column(name = "level", columnDefinition = "varchar(50) comment '紧急程度'")
    private String level;

    @Column(name = "position", columnDefinition = "varchar(255) comment '位置'")
    private String position;

    @Column(name = "report_desc", columnDefinition = "varchar(255) comment '描述'")
    private String reportDesc;

    @Column(name = "report_time", columnDefinition = "dateTime comment '报告时间'")
    private Instant reportTime;

    @Column(name = "reporter_id", columnDefinition = "int comment '报告人ID'")
    private Long reporterId;

    @Column(name = "delete_flag", columnDefinition = "tinyint comment '删除标记'")
    private Boolean deleteFlag;
}
