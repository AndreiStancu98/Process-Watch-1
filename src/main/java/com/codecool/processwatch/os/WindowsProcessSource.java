package com.codecool.processwatch.os;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.User;
import com.codecool.processwatch.domain.WindowsProcess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * A process source using the Java {@code ProcessHandle} API to retrieve information
 * about the current processes.
 */
public class WindowsProcessSource implements ProcessSource {
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Process> getProcesses() {

        ArrayList<Process> processWins = new ArrayList<>();
        try {
            String process;
            var p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist /fo csv /nh /v");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((process = input.readLine()) != null) {
                String[] columns = process.substring(1, process.length() - 1).split("\",\"");
                processWins.add(new WindowsProcess(
                        columns[WindowsColumn.NAME.index],
                        columns[WindowsColumn.PID.index],
                        columns[WindowsColumn.WINDOW_TITLE.index],
                        columns[WindowsColumn.SESSION_NAME.index],
                        columns[WindowsColumn.SESSION_NUMBER.index],
                        new User(columns[WindowsColumn.USER.index]),
                        columns[WindowsColumn.MEM_USAGE.index],
                        columns[WindowsColumn.STATUS.index],
                        columns[WindowsColumn.UPTIME.index]
                ));
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processWins.stream();
    }
}
