package com.codecool.processwatch.gui;

import com.codecool.processwatch.domain.ProcessDisplay;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.ProcessWatchApp;
import com.codecool.processwatch.queries.SelectAll;

/**
 * An application using a real process source and a JavaFx process display.
 */
public class App extends ProcessWatchApp {

    public App(ProcessSource processSource, ProcessDisplay processDisplay) {
        super(processSource, processDisplay, new SelectAll());
    }
}
