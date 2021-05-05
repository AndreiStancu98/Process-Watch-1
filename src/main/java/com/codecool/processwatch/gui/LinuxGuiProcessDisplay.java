package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.LinuxProcess;
import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessDisplay;
import javafx.collections.ObservableList;

/**
 * A process display that interacts with a JavaFX gui via a shared
 * observable list instance.
 */
public class LinuxGuiProcessDisplay extends ProcessDisplay  {
    private final ObservableList<LinuxProcessView> displayList;

    /**
     * Create the display logic.
     *
     * @param displayList an observable list manipulated by this class and
     *                    observed by the application window.
     */
    public LinuxGuiProcessDisplay(ObservableList<LinuxProcessView> displayList) {
        this.displayList = displayList;
    }

    @Override
    protected void clear() {
        displayList.clear();
    }

    @Override
    protected void addProcess(Process proc) {
        displayList.add(new LinuxProcessView((LinuxProcess) proc));
    }
}
