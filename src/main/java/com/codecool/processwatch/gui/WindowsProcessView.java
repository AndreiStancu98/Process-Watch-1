package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.WindowsProcess;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 * A view representing a process.
 * <p>
 * It mirrors the fields of a process as javaFX properties.
 */
public class WindowsProcessView {
    public WindowsProcessView(WindowsProcess windowsProcess) {
        this.name = new SimpleStringProperty(windowsProcess.getName());
        this.pid = new SimpleStringProperty(windowsProcess.getPid());
        this.windowTitle = new SimpleStringProperty(windowsProcess.getWindowTitle());
        this.sessionName = new SimpleStringProperty(windowsProcess.getSessionName());
        this.sessionNumber = new SimpleStringProperty(windowsProcess.getSessionNumber());
        this.user = new SimpleStringProperty(windowsProcess.getUser());
        this.memUsage = new SimpleStringProperty(windowsProcess.getMemUsage());
        this.status = new SimpleStringProperty(windowsProcess.getStatus());
        this.uptime = new SimpleStringProperty(windowsProcess.getUptime());
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

    private StringProperty windowTitle;
    public void setWindowTitle(String value) { windowTitleProperty().set(value); }
    public String getWindowTitle() { return windowTitleProperty().get(); }
    public StringProperty windowTitleProperty() {
        if (windowTitle == null) {
            windowTitle = new SimpleStringProperty(this, "windowTitle");
        }
        return windowTitle;
    }

    private StringProperty sessionName;
    public void setSessionName(String value) { sessionNameProperty().set(value); }
    public String getSessionName() { return sessionNameProperty().get(); }
    public StringProperty sessionNameProperty() {
        if (sessionName == null) {
            sessionName = new SimpleStringProperty(this, "sessionName");
        }
        return sessionName;
    }

    private StringProperty sessionNumber;
    public void setSessionNumber(String value) { sessionNumberProperty().set(value); }
    public String getSessionNumber() { return sessionNumberProperty().get(); }
    public StringProperty sessionNumberProperty() {
        if (sessionNumber == null) {
            sessionNumber = new SimpleStringProperty(this, "sessionNumber");
        }
        return sessionNumber;
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

    private StringProperty memUsage;
    public void setMemUsage(String value) { memUsageProperty().set(value); }
    public String getMemUsage() { return memUsageProperty().get(); }
    public StringProperty memUsageProperty() {
        if (memUsage == null) {
            memUsage = new SimpleStringProperty(this, "memUsage");
        }
        return memUsage;
    }

    private StringProperty status;
    public void setStatus(String value) { statusProperty().set(value); }
    public String getStatus() { return statusProperty().get(); }
    public StringProperty statusProperty() {
        if (status == null) {
            status = new SimpleStringProperty(this, "status");
        }
        return status;
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



