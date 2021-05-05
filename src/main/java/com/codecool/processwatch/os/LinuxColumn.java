package com.codecool.processwatch.os;

public enum LinuxColumn {
    PID(1), USER(0), UPTIME(3), COMMAND(4), CPU_USAGE(2 );

    public final int index;

    LinuxColumn(int index) {
        this.index = index;
    }
}
