package Part_B;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Task<T> extends FutureTask<T> implements Comparable<Task<T>> , Callable{

    private Callable operation;
    private final int priority;
    private TaskType taskType;


    // private constructor for the createTask factory method that gets 2 parameters
    private Task(Callable<T> operation,TaskType type) {
        super(operation);
        this.priority = type.getPriorityValue();
        this.taskType=type;
    }

    // private constructor for the createTask factory method that gets one parameter
    private Task(Callable<T> operation) {
        this(operation, TaskType.OTHER);
    }

    /**
     * this is a factory method that creates a new Task object, that gets callable and TaskType.
     * @param operation - callable object
     * @param type - TaskType enum
     * @return new Task object
     */
    public static Task createTask(Callable operation, TaskType type) {
        return new Task(operation, type);
    }

    /**
     * this is a factory method that creates a new Task object, that gets callable object.
     * in this case, the TaskType is set to default which is "OTHER"(3).
     * @param operation - callable object
     * @return new Task object
     */
    public static Task createTask(Callable operation) {
        return new Task(operation);
    }

    /**
     * getter method for the priority attribute
     * @return priority attribute
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * getter method for the TaskType attribute
     * @return taskType attribute
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * getter method for the callable attribute
     * @return callable attribute
     */
    public Callable getOperation()
    {
        return operation;
    }

    /**
     * compare 2 Tasks by their priority values.
     * @param o - the object to be compared.
     * @return
     *  0 if Tasks objects are equal by priority values
     * -1 if this Task's priorityValue is smaller than other's priorityValue
     *  1 if this Task's priorityValue is bigger than other's priorityValue
     */
    @Override
    public int compareTo(Task o) {
        return this.priority - o.getPriority();
    }

    /**
     * this is the equals method that any Object has, designed to this Task object.
     * the comparison is by the callable objects and the priority attribute.
     * @param o object
     * @return true if they are equals, false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task<?> task = (Task<?>) o;
        if(this.getCallable() != ((Task<?>) other).getCallable()) return false;
        return priority == task.priority;
    }

    /**
     * HashCode function.
     * @return an integer which is value represents the hash value of the input object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(priority);
    }

    /**
     * @return a string representation of this Task.
     */
    @Override
    public String toString() {
        return "Task priority = " + priority + ", is done = " +super.isDone();
    }


    /**
     * this is the implementation for the call method that belongs to the Callable interface.
     * @return @return priority attribute.
     * @throws Exception
     */
    @Override
    public Object call() throws Exception
    {
        try
        {
            return this.priority;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}