package com.codecool.processwatch.os;

import com.codecool.processwatch.domain.LinuxProcess;
import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * A process source using the Java {@code ProcessHandle} API to retrieve information
 * about the current processes.
 */
public class LinuxProcessSource implements ProcessSource {
    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Process> getProcesses() {

        ArrayList<Process> processesLin = new ArrayList<>();
        try {
            String process;
            var p = Runtime.getRuntime().exec("ps --no-headers -Ao '%U,%p,%C,%x,%c'");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((process = input.readLine()) != null) {
                String[] columns = process.substring(1, process.length() - 1).split(",");
                processesLin.add(new LinuxProcess(
                        columns[LinuxColumn.COMMAND.index].substring(columns[LinuxColumn.COMMAND.index].lastIndexOf('/') == -1 ? 0 : 1+ columns[LinuxColumn.COMMAND.index].lastIndexOf('/')).strip(),
                        columns[LinuxColumn.PID.index].strip(),
                        new User(columns[LinuxColumn.USER.index].strip()),
                        columns[LinuxColumn.CPU_USAGE.index].strip(),
                        columns[LinuxColumn.UPTIME.index].strip(),
                        columns[LinuxColumn.COMMAND.index].strip()
                ));
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processesLin.stream();
    }
}
