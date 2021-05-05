package com.codecool.processwatch.queries;

import com.codecool.processwatch.domain.LinuxProcess;
import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.Query;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LinuxSearch implements Query {

    private final String term;

    public LinuxSearch(String term) {
        this.term = term;
    }

    @Override
    public Stream<Process> run(Stream<Process> input) {
        return input.filter(process -> {
            LinuxProcess linuxProcess = (LinuxProcess) process;
            return
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getName()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getPid()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getCpuUsage()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getCommand()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getUser()).find() ||
                Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(linuxProcess.getUptime()).find();
        });
    }
}
