package com.codecool.processwatch.os;

public enum WindowsColumn {
    NAME(0), PID(1), SESSION_NAME(2), SESSION_NUMBER(3), MEM_USAGE(4),
    STATUS(5), USER(6), UPTIME(7), WINDOW_TITLE(8);

    public final int index;

    WindowsColumn(int index) {
        this.index = index;
    }
}
