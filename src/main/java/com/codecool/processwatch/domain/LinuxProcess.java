package com.codecool.processwatch.domain;

/**
 * A class representing a displayable and queryable process.
 */
public class LinuxProcess extends Process {
    private final String cpuUsage;
    private final String command;
    private final String uptime;

    public LinuxProcess(String name, String pid, User user, String cpuUsage, String uptime, String command) {
        super(name, pid, user);
        this.cpuUsage = cpuUsage;
        this.command = command;
        this.uptime = uptime;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public String getCommand() {
        return command;
    }

    public String getUptime() {
        return uptime;
    }
}
