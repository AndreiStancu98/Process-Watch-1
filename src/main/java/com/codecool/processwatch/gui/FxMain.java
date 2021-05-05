package com.codecool.processwatch.gui;

import com.codecool.processwatch.os.LinuxProcessSource;
import com.codecool.processwatch.os.WindowsProcessSource;
import com.codecool.processwatch.queries.SelectAll;
import com.codecool.processwatch.queries.WindowsSearch;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The JavaFX application Window.
 */
@SuppressWarnings("DuplicatedCode")
public class FxMain extends Application {
    private static final String TITLE = "Process Watch";

    private App app;

    private final static ArrayList<String> selectedPIDs = new ArrayList<>();
    public static void selectPid(String pid) {
        selectedPIDs.add(pid);
    }
    public static void removePid(String pid) {
        selectedPIDs.remove(pid);
    }

    private final static String aboutProgram = "Process Watch\n" +
            "\n" +
            "This application lists all processes that are currently running on your computer.\n" +
            "\n" +
            "\n" +
            "Features:\n" +
            "\n" +
            "-> Refresh Button: It refreshes the list of processes.\n" +
            "\n" +
            "-> Process filter: It provides a search field for searching processes on any criteria or filter  ASCENDING/DESCENDING, based  on headers.\n" +
            "\n" +
            "-> Process kill Button: It ends the selected processes, it could be single or multiple processes.\n" +
            "\n" +
            "-> Help information Buttons (?): Each functional button has a ? button, which provides information on what the buttons do.\n" +
            "\n" +
            "-> About Button: It provides this description  about the whole app.";

    /**
     * Entrypoint for the javafx:run maven task.
     *
     * @param args an array of the command line parameters.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Build the application window and set up event handling.
     *
     * @param primaryStage a stage created by the JavaFX runtime.
     */
    public void start(Stage primaryStage) {

        primaryStage.setTitle(TITLE);
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        //region Buttons
        var refreshButton = new Button("Refresh");
        refreshButton.setOnAction(refreshEvent -> app.refresh());
        refreshButton.setPrefHeight(25);
        refreshButton.setPrefWidth(75);

        var killButton = new Button("Kill");
        killButton.setPrefHeight(25);
        killButton.setPrefWidth(75);

        var About = new Button("About");
        About.setOnAction(ignoreEvent -> alert(aboutProgram));

        var searchInput = new TextField();
        searchInput.setPromptText("search...");
        searchInput.setOnAction(actionEvent -> {
            if (!searchInput.getText().isEmpty()) {
                app.setQuery(new WindowsSearch(searchInput.getText()));
            } else {
                app.setQuery(new SelectAll());
            }
            app.refresh();
        });
        //endregion

        //region ButtonHelpers
        var refreshButtonHelper = new Button("?");
        refreshButtonHelper.setOnAction(ignoreEvent -> alert("Press to refresh process list!"));

        var killButtonHelper = new Button("?");
        killButtonHelper.setOnAction(ignoreEvent -> alert("This button kills the selected processes!"));

        var searchBarHelper = new Button("?");
        searchBarHelper.setOnAction(ignoreEvent -> alert("Type and press ENTER to search by any field"));
        //endregion

        //region Boxing
        var box = new VBox();
        var topLeftButtonsBox = new HBox(refreshButton, refreshButtonHelper);
        topLeftButtonsBox.setAlignment(Pos.TOP_LEFT);
        var topPaddingBox = new HBox();
        HBox.setHgrow(topPaddingBox, Priority.ALWAYS);
        var topRightButtonsBox = new HBox(searchInput, searchBarHelper, About);
        topRightButtonsBox.setAlignment(Pos.TOP_RIGHT);
        var topButtonsBox = new HBox(topLeftButtonsBox, topPaddingBox, topRightButtonsBox);
        var bottomButtonsBox = new HBox(killButton, killButtonHelper);
        bottomButtonsBox.setAlignment(Pos.BOTTOM_RIGHT);
        var scene = new Scene(box, 1280, 720);
        var elements = box.getChildren();
        //endregion

        if (SystemUtils.IS_OS_WINDOWS) {
            ObservableList<WindowsProcessView> displayList = observableArrayList();
            app = new App(new WindowsProcessSource(), new WindowsGuiProcessDisplay(displayList));
            var tableView = new TableView<>(displayList);

            TableColumn selectCol = new TableColumn("Select");
            selectCol.setCellValueFactory(new PropertyValueFactory<WindowsProcessView, String>("Select"));

            var processNameColumn = new TableColumn<WindowsProcessView, String>("Name");
            processNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            var pidColumn = new TableColumn<WindowsProcessView, String>("Process ID");
            pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
            var windowTitleColumn = new TableColumn<WindowsProcessView, String>("Window Title");
            windowTitleColumn.setCellValueFactory(new PropertyValueFactory<>("windowTitle"));
            var sessionNameColumn = new TableColumn<WindowsProcessView, String>("Session Name");
            sessionNameColumn.setCellValueFactory(new PropertyValueFactory<>("sessionName"));
            var sessionNumberColumn = new TableColumn<WindowsProcessView, String>("Session Number");
            sessionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sessionNumber"));
            var userColumn = new TableColumn<WindowsProcessView, String>("User");
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            var memUsageColumn = new TableColumn<WindowsProcessView, String>("Memory Usage");
            memUsageColumn.setCellValueFactory(new PropertyValueFactory<>("memUsage"));
            var statusColumn = new TableColumn<WindowsProcessView, String>("Status");
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            var uptimeColumn = new TableColumn<WindowsProcessView, String>("Uptime");
            uptimeColumn.setCellValueFactory(new PropertyValueFactory<>("uptime"));

            tableView.getColumns().add(selectCol);
            tableView.getColumns().add(processNameColumn);
            tableView.getColumns().add(pidColumn);
            tableView.getColumns().add(windowTitleColumn);
            tableView.getColumns().add(sessionNameColumn);
            tableView.getColumns().add(sessionNumberColumn);
            tableView.getColumns().add(userColumn);
            tableView.getColumns().add(memUsageColumn);
            tableView.getColumns().add(statusColumn);
            tableView.getColumns().add(uptimeColumn);

            var selectAllCheckbox = new CheckBox("Select All");
            selectAllCheckbox.setOnAction(ActionEvent -> {
                if (selectAllCheckbox.isSelected()) {
                    tableView.getItems().forEach(item -> item.getSelect().setSelected(true));
                } else {
                    tableView.getItems().forEach(item -> item.getSelect().setSelected(false));
                }
            });

            killButton.setOnAction(actionEvent -> {
                selectedPIDs.forEach(pid -> {
                    String cmd = "taskkill /F /PID " + pid;
                    try {
                        Runtime.getRuntime().exec(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                app.refresh();
            });

            VBox.setVgrow(tableView, Priority.ALWAYS);
            elements.addAll(topButtonsBox,selectAllCheckbox, tableView, bottomButtonsBox);
        } else if (SystemUtils.IS_OS_LINUX) {
            ObservableList<LinuxProcessView> displayList = observableArrayList();
            app = new App(new LinuxProcessSource(), new LinuxGuiProcessDisplay(displayList));
            var tableView = new TableView<>(displayList);

            TableColumn selectCol = new TableColumn("Select");
            selectCol.setCellValueFactory(new PropertyValueFactory<LinuxProcessView, String>("Select"));

            var processNameColumn = new TableColumn<LinuxProcessView, String>("Name");
            processNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            var pidColumn = new TableColumn<LinuxProcessView, String>("Process ID");
            pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
            var userColumn = new TableColumn<LinuxProcessView, String>("User");
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            var cpuUsageColumn = new TableColumn<LinuxProcessView, String>("CPU Usage");
            cpuUsageColumn.setCellValueFactory(new PropertyValueFactory<>("cpuUsage"));
            var uptimeColumn = new TableColumn<LinuxProcessView, String>("Uptime");
            uptimeColumn.setCellValueFactory(new PropertyValueFactory<>("uptime"));
            var commandColumn = new TableColumn<LinuxProcessView, String>("Command");
            commandColumn.setCellValueFactory(new PropertyValueFactory<>("command"));

            tableView.getColumns().add(selectCol);
            tableView.getColumns().add(processNameColumn);
            tableView.getColumns().add(pidColumn);
            tableView.getColumns().add(userColumn);
            tableView.getColumns().add(cpuUsageColumn);
            tableView.getColumns().add(uptimeColumn);
            tableView.getColumns().add(commandColumn);

            var selectAllCheckbox = new CheckBox("Select All");
            selectAllCheckbox.setOnAction(ActionEvent -> {
                if (selectAllCheckbox.isSelected()) {
                    tableView.getItems().forEach(item -> item.getSelect().setSelected(true));
                } else {
                    tableView.getItems().forEach(item -> item.getSelect().setSelected(false));
                }
            });

            killButton.setOnAction(actionEvent -> {
                selectedPIDs.forEach(pid -> {
                    String cmd = "kill " + pid;
                    try {
                        Runtime.getRuntime().exec(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                app.refresh();
            });

            VBox.setVgrow(tableView, Priority.ALWAYS);
            elements.addAll(topButtonsBox, selectAllCheckbox, tableView, bottomButtonsBox);
        } else {
            System.out.println("Sorry, unsupported OS, exiting...");
            System.exit(1);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
