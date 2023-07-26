package com.htb.hosting.rmi.quarantine;

import java.io.File;
import java.io.Serializable;

public class QuarantineConfiguration implements Serializable {

    private static final long serialVersionUID = 5027977764505712914L;

    private final File quarantineDirectory;

    private final File monitorDirectory;

    private final String clamHost;

    private final int clamPort;

    private final int clamTimeout;

    public QuarantineConfiguration(File quarantineDirectory, File monitorDirectory, String clamHost, int clamPort, int clamTimeout) {
        this.quarantineDirectory = quarantineDirectory;
        this.monitorDirectory = monitorDirectory;
        this.clamHost = clamHost;
        this.clamPort = clamPort;
        this.clamTimeout = clamTimeout;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QuarantineConfiguration)) {
            return false;
        }
        QuarantineConfiguration other = (QuarantineConfiguration) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (getClamPort() != other.getClamPort()) {
            return false;
        }
        if (getClamTimeout() != other.getClamTimeout()) {
            return false;
        }
        Object this$quarantineDirectory = getQuarantineDirectory(), other$quarantineDirectory = other.getQuarantineDirectory();
        if ((this$quarantineDirectory == null) ? (other$quarantineDirectory != null) : !this$quarantineDirectory.equals(other$quarantineDirectory)) {
            return false;
        }
        Object this$monitorDirectory = getMonitorDirectory(), other$monitorDirectory = other.getMonitorDirectory();
        if ((this$monitorDirectory == null) ? (other$monitorDirectory != null) : !this$monitorDirectory.equals(other$monitorDirectory)) {
            return false;
        }
        Object this$clamHost = getClamHost(), other$clamHost = other.getClamHost();
        return !((this$clamHost == null) ? (other$clamHost != null) : !this$clamHost.equals(other$clamHost));
    }

    protected boolean canEqual(Object other) {
        return other instanceof QuarantineConfiguration;
    }

//  public int hashCode() {
//    int PRIME = 59;
//    result = 1;
//    result = result * 59 + getClamPort();
//    result = result * 59 + getClamTimeout();
//    Object $quarantineDirectory = getQuarantineDirectory();
//    result = result * 59 + (($quarantineDirectory == null) ? 43 : $quarantineDirectory.hashCode());
//    Object $monitorDirectory = getMonitorDirectory();
//    result = result * 59 + (($monitorDirectory == null) ? 43 : $monitorDirectory.hashCode());
//    Object $clamHost = getClamHost();
//    return result * 59 + (($clamHost == null) ? 43 : $clamHost.hashCode());
//  }
    public String toString() {
        return "QuarantineConfiguration(quarantineDirectory=" + getQuarantineDirectory() + ", monitorDirectory=" + getMonitorDirectory() + ", clamHost=" + getClamHost() + ", clamPort=" + getClamPort() + ", clamTimeout=" + getClamTimeout() + ")";
    }

    public File getQuarantineDirectory() {
        return this.quarantineDirectory;
    }

    public File getMonitorDirectory() {
        return this.monitorDirectory;
    }

    public String getClamHost() {
        return this.clamHost;
    }

    public int getClamPort() {
        return this.clamPort;
    }

    public int getClamTimeout() {
        return this.clamTimeout;
    }
}
