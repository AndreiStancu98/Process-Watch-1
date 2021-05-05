package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessDisplay;
import com.codecool.processwatch.domain.WindowsProcess;
import javafx.collections.ObservableList;

/**
 * A process display that interacts with a JavaFX gui via a shared
 * observable list instance.
 */
public class WindowsGuiProcessDisplay extends ProcessDisplay  {
    private final ObservableList<WindowsProcessView> displayList;

    /**
     * Create the display logic.
     *
     * @param displayList an observable list manipulated by this class and
     *                    observed by the application window.
     */
    public WindowsGuiProcessDisplay(ObservableList<WindowsProcessView> displayList) {
        this.displayList = displayList;
    }

    @Override
    protected void clear() {
        displayList.clear();
    }

    @Override
    protected void addProcess(Process proc) {
        displayList.add(new WindowsProcessView((WindowsProcess) proc));
    }
}
