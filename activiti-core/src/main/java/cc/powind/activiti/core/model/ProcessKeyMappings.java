package cc.powind.activiti.core.model;

import java.util.HashMap;
import java.util.Map;

public class ProcessKeyMappings {

    private static final Map<String, String> mappings = new HashMap<>();

    static {
        mappings.put("system_holiday", "XJ");
        mappings.put("system_hidden_hazard", "YH");
    }

    public static String getAliasName(String processKey) {
        return mappings.getOrDefault(processKey, "");
    }
}
