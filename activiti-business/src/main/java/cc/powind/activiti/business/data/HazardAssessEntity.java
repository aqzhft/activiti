package cc.powind.activiti.business.data;

import cc.powind.activiti.business.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@javax.persistence.Table(name = "business_hazard_access")
@org.hibernate.annotations.Table(appliesTo = "business_hazard_access", comment = "隐患评估")
public class HazardAssessEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int comment 'ID'")
    private Long id;

    @Column(name = "report_id", columnDefinition = "int comment '所属报告ID'")
    private Long reportId;

    @Column(name = "need_rectify", columnDefinition = "tinyint comment '是否需要真该'")
    private Boolean needRectify;

    @Column(name = "reason", columnDefinition = "varchar(255) comment '理由'")
    private String reason;

    @Column(name = "assessor_id", columnDefinition = "int comment '评估人ID'")
    private Long assessorId;

    @Column(name = "assess_time", columnDefinition = "dateTime comment '评估时间'")
    private Instant assessTime;

    @Column(name = "delete_flag", columnDefinition = "tinyint comment '删除标记'")
    private Boolean deleteFlag;
}
