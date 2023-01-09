package Part_B;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask<T> implements Comparable<Task<T>> {

    private final int priority;

    public Task(Callable<T> operation, int priority) {
        super(operation);
        this.priority = priority;
    }

    public Task(Callable<T> operation) {
        this(operation, 3);
    }

    public Task(Callable<T> operation, TaskType type) {
        this(operation, type.getPriorityValue());
    }

    public static Task createTask(Callable operation, TaskType type) {
        return new Task(operation, type);
    }

    public static Task createTask(Callable operation) {
        return new Task(operation);
    }

    public int getPriority() {
        return this.priority;
    }

    /**
     * compare between 2 priority of task
     *
     * @param o task to compare
     * @return
     */
    @Override
    public int compareTo(Task o) {
        return this.priority - o.getPriority();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(priority);
    }

    @Override
    public String toString() {
        return "Task priority = " + priority + ", is done = " +super.isDone();
    }
}