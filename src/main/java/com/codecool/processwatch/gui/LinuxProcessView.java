package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.LinuxProcess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class LinuxProcessView {
    public LinuxProcessView(LinuxProcess linuxProcess) {
        this.name = new SimpleStringProperty(linuxProcess.getName());
        this.pid = new SimpleStringProperty(linuxProcess.getPid());
        this.user = new SimpleStringProperty(linuxProcess.getUser());

        this.cpuUsage = new SimpleStringProperty(linuxProcess.getCpuUsage());
        this.command = new SimpleStringProperty(linuxProcess.getCommand());
        this.uptime = new SimpleStringProperty(linuxProcess.getUptime());
        this.select = new CheckBox();

        select.setOnAction(actionEvent -> {
            if (select.isSelected()) {
                FxMain.selectPid(pid.getValue());
            }
            else {
                FxMain.removePid(pid.getValue());
            }
        });
    }

    private CheckBox select;
    public CheckBox getSelect() {
        return select;
    }
    public void setSelect(CheckBox select) {
        this.select = select;
    }

    private StringProperty name;
    public void setName(String value) { nameProperty().set(value); }
    public String getName() { return nameProperty().get(); }
    public StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(this, "name");
        }
        return name;
    }

    private StringProperty pid;
    public void setPid(String value) { pidProperty().set(value); }
    public String getPid() { return pidProperty().get(); }
    public StringProperty pidProperty() {
        if (pid == null) {
            pid = new SimpleStringProperty(this, "pid");
        }
        return pid;
    }

    private StringProperty user;
    public void setUser(String value) { userProperty().set(value); }
    public String getUser() { return userProperty().get(); }
    public StringProperty userProperty() {
        if (user == null) {
            user = new SimpleStringProperty(this, "user");
        }
        return user;
    }

    private StringProperty cpuUsage;
    public void setCpuUsage(String value) { cpuUsageProperty().set(value); }
    public String getCpuUsage() { return cpuUsageProperty().get(); }
    public StringProperty cpuUsageProperty() {
        if (cpuUsage == null) {
            cpuUsage = new SimpleStringProperty(this, "cpuUsage");
        }
        return cpuUsage;
    }


    private StringProperty command;
    public void setCommand(String value) { commandProperty().set(value); }
    public String getCommand() { return commandProperty().get(); }
    public StringProperty commandProperty() {
        if (command == null) {
            command = new SimpleStringProperty(this, "command");
        }
        return command;
    }

    private StringProperty uptime;
    public void setUptime(String value) { uptimeProperty().set(value); }
    public String getUptime() { return uptimeProperty().get(); }
    public StringProperty uptimeProperty() {
        if (uptime == null) {
            uptime = new SimpleStringProperty(this, "uptime");
        }
        return uptime;
    }

    private static String merge(String[] strings) {
        return String.join(" ", strings);
    }
}
