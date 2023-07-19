package cc.powind.activiti.core.model;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessDefine {

    /**
     * 编号
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属流程
     */
    private List<Process> processList = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Process> getProcessList() {
        return processList;
    }

    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    public List<Instance> getInstanceList() {
        return (processList == null || processList.isEmpty()) ? Collections.emptyList() : processList.stream().map(Process::getInstanceList).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
