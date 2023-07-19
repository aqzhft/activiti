package cc.powind.activiti.core.model;

public class RequestMessage {

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数值
     */
    private Object[] parameterValue;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Object[] parameterValue) {
        this.parameterValue = parameterValue;
    }
}
