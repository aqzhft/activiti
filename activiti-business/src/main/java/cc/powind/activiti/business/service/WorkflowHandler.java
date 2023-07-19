package cc.powind.activiti.business.service;

public interface WorkflowHandler {

    void handle(String businessKey);

    String supportProcessKey();
}
