package cc.powind.activiti.business.data;

import cc.powind.activiti.business.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@javax.persistence.Table(name = "business_hazard_accept")
@org.hibernate.annotations.Table(appliesTo = "business_hazard_accept", comment = "隐患整改验收")
public class HazardAcceptEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int comment 'ID'")
    private Long id;

    @Column(name = "report_id", columnDefinition = "int comment '所属报告ID'")
    private Long reportId;

    @Column(name = "rectify_id", columnDefinition = "int comment '所属整改ID'")
    private Long rectifyId;

    @Column(name = "qualified", columnDefinition = "tinyint comment '是否合格'")
    private Boolean qualified;

    @Column(name = "remark", columnDefinition = "varchar(255) comment '备注信息'")
    private String remark;

    @Column(name = "acceptor_id", columnDefinition = "int comment '验收人ID'")
    private Long acceptorId;

    @Column(name = "accept_time", columnDefinition = "dateTime comment '验收时间'")
    private Instant acceptTime;

    @Column(name = "delete_flag", columnDefinition = "tinyint comment '删除标记'")
    private Boolean deleteFlag;
}
