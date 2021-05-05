package com.codecool.processwatch.domain;

public abstract class Process {
    private final String name;
    private final String pid;
    private final User user;

    public Process(String name, String pid, User user) {
        this.name = name;
        this.pid = pid;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getUser() {
        return user.getName();
    }


    /**
     * Equality check with other processes.
     * <p>
     * Uses the process ID for identity.
     * If the other process has the same pid then it will be considered equal.
     *
     * @param o the other process to compare for equality.
     *
     * @return {@code true} if {@code o} has the same pid.
     */
    @Override
    public boolean equals(Object o) {
        var clazz = getClass();
        if (!clazz.isInstance(o)) {
            return false;
        }

        if (this == o) {
            return true;
        }

        var other = clazz.cast(o);

        return this.pid.equals(other.pid);
    }

    /**
     * Get a string representation of the process.
     * <p>
     * It displays the pid, ppid and user.
     *
     * @return a String representing the process.
     */
    @Override
    public String toString() {
        return String.format("Process {pid = %s, name = %s, user = %s}",
                pid, name, user.getName());
    }

    /**
     * Generate a hash value for the process.
     * <p>
     * The value of the hash is only based on the process ID.
     * Other values are not considered.
     *
     * @return the hash of the pid.
     */
    @Override
    public int hashCode() {
        @SuppressWarnings("WrapperTypeMayBePrimitive")
        var objPid = Long.valueOf(pid);
        return objPid.hashCode();
    }
}
