package cc.powind.activiti.business.data;

import cc.powind.activiti.business.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@javax.persistence.Table(name = "business_hazard_rectify")
@org.hibernate.annotations.Table(appliesTo = "business_hazard_rectify", comment = "隐患整改")
public class HazardRectifyEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int comment 'ID'")
    private Long id;

    @Column(name = "report_id", columnDefinition = "int comment '所属报告ID'")
    private Long reportId;

    @Column(name = "measure", columnDefinition = "varchar(255) comment '采取的措施'")
    private String measure;

    @Column(name = "rectify_time", columnDefinition = "dateTime comment '整改时间'")
    private Instant rectifyTime;

    @Column(name = "duration", columnDefinition = "int comment '持续时长（小时）'")
    private Integer duration;

    @Column(name = "remark", columnDefinition = "varchar(255) comment '备注信息'")
    private String remark;

    @Column(name = "rectify_header_id", columnDefinition = "int comment '责任人ID'")
    private Long rectifyHeaderId;

    @Column(name = "record_time", columnDefinition = "dateTime comment '提交时间'")
    private Instant recordTime;

    @Column(name = "delete_flag", columnDefinition = "tinyint comment '删除标记'")
    private Boolean deleteFlag;
}
