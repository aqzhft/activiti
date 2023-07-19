package cc.powind.activiti.business.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@javax.persistence.Table(name = "business_holiday")
@org.hibernate.annotations.Table(appliesTo = "business_holiday", comment = "请假申请表")
public class HolidayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int comment 'ID'")
    private Long id;

    @Column(name = "holiday_key", columnDefinition = "varchar(20) comment '申请编号'")
    private String holidayKey;

    @Column(name = "apply_user_id", columnDefinition = "varchar(20) comment '申请人用户ID'")
    private String applyUserId;

    @Column(name = "apply_user_name", columnDefinition = "varchar(20) comment '申请人姓名'")
    private String applyUserName;

    @Column(name = "day_size", columnDefinition = "int comment '请假天数'")
    private Integer daySize;

    @Column(name = "apply_time", columnDefinition = "dateTime comment '申请时间'")
    private Instant applyTime;

    @Column(name = "description", columnDefinition = "varchar(255) comment '描述信息'")
    private String description;

    @Column(name = "submitted", columnDefinition = "tinyint default 0 comment '是否提交'")
    private Boolean submitted;
}
