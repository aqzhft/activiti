package cc.powind.activiti.impl.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeployRequest {

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程key
     */
    private String key;

    /**
     * xml数据
     */
    private String data;

    /**
     * 文件名称
     */
    private String fileName;
}
