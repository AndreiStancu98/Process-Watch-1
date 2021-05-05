package com.codecool.processwatch.queries;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.Query;
import com.codecool.processwatch.domain.WindowsProcess;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WindowsSearch implements Query {

    private final String term;

    public WindowsSearch(String term) {
        this.term = term;
    }

    @Override
    public Stream<Process> run(Stream<Process> input) {
        return input.filter(process -> {
            WindowsProcess windowsProcess = (WindowsProcess) process;
            return
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getName()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getPid()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getWindowTitle()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getSessionName()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getSessionNumber()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getUser()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getMemUsage()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getStatus()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(windowsProcess.getUptime()).find();
        });
    }
}
