package org.traceatlas.Scriptainer.dataobjects;


public class ExtensionData {
    private String extensionName;
    private String extensionMetricValue;
    private String extensionHostName;

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getExtensionMetricValue() {
        return extensionMetricValue;
    }

    public void setExtensionMetricValue(String extensionMetricValue) {
        this.extensionMetricValue = extensionMetricValue;
    }

    public String getExtensionHostName() {
        return extensionHostName;
    }

    public void setExtensionHostName(String extensionHostName) {
        this.extensionHostName = extensionHostName;
    }

    public String getExtensionHostIpAddress() {
        return extensionHostIpAddress;
    }

    public void setExtensionHostIpAddress(String extensionHostIpAddress) {
        this.extensionHostIpAddress = extensionHostIpAddress;
    }

    private String extensionHostIpAddress;
}
