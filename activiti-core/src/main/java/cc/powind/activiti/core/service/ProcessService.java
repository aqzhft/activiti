package cc.powind.activiti.core.service;

import cc.powind.activiti.core.model.Process;
import cc.powind.activiti.core.model.ProcessDefine;

import java.util.List;

public interface ProcessService {

    List<ProcessDefine> findAllRuntimeDefine();

    List<Process> findAllDetail();

    Process findByProcessId(String processId);

    List<Process> findAll();

    String getXml(String processId);

    String getXmlFromProcessKey(String processKey);

    void xml(String fileName, byte[] bytes, String processName, String processKey);
}
