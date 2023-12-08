package org.traceatlas.Scriptainer.dataobjects;

public class BuiltInMetricData {
    public String getMetricDataName() {
        return metricDataName;
    }

    public void setMetricDataName(String metricDataName) {
        this.metricDataName = metricDataName;
    }

    public String getMetricDataValue() {
        return metricDataValue;
    }

    public void setMetricDataValue(String metricDataValue) {
        this.metricDataValue = metricDataValue;
    }

    public String getMetricDataHostName() {
        return metricDataHostName;
    }

    public void setMetricDataHostName(String metricDataHostName) {
        this.metricDataHostName = metricDataHostName;
    }

    public String getMetricDataHostIpAddress() {
        return metricDataHostIpAddress;
    }

    public void setMetricDataHostIpAddress(String metricDataHostIpAddress) {
        this.metricDataHostIpAddress = metricDataHostIpAddress;
    }

    private String metricDataName;
    private String metricDataValue;
    private String metricDataHostName;
    private String metricDataHostIpAddress;
}
