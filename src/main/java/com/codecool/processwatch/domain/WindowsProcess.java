package com.codecool.processwatch.domain;

/**
 * A class representing a displayable and queryable process.
 */
public class WindowsProcess extends Process {
    private final String windowTitle;
    private final String sessionName;
    private final String sessionNumber;
    private final String memUsage;
    private final String status;
    private final String uptime;

    public WindowsProcess(String name, String pid, String windowTitle, String sessionName, String sessionNumber, User user, String memUsage, String status, String uptime) {
        super(name, pid, user);
        this.windowTitle = windowTitle;
        this.sessionName = sessionName;
        this.sessionNumber = sessionNumber;
        this.memUsage = memUsage;
        this.status = status;
        this.uptime = uptime;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public String getMemUsage() {
        return memUsage;
    }

    public String getStatus() {
        return status;
    }

    public String getUptime() {
        return uptime;
    }
}
