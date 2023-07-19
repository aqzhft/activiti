package cc.powind.activiti.business.base;

public interface BaseEntity {

    Long getId();

    Boolean getDeleteFlag();

    void setDeleteFlag(Boolean deleteFlag);
}
