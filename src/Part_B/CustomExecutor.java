package Part_B;

import java.util.concurrent.*;

public class CustomExecutor<T> extends ThreadPoolExecutor {

    private int maxPriority;
    private int [] arrOfPriority;

    // constructor
    public CustomExecutor(){
        super(Runtime.getRuntime().availableProcessors()/2,Runtime.getRuntime().availableProcessors()-1,300, TimeUnit.MILLISECONDS,new PriorityBlockingQueue<>());
        this.maxPriority=0;
        this.arrOfPriority=new int[10];
    }

    /**
     * Submits a Callable task for execution and returns a Future representing that task.
     * @param task - the task to submit.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Task <T> task){
        if (task == null)
            throw new NullPointerException();
        return mySubmit(task);
    }

    /**
     * Submits a Callable task for execution and returns a Future representing that task.
     * @param operation - the operation callable to submit.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Callable <T> operation){
        if (operation == null)
            throw new NullPointerException();
        Task<T> task = Task.createTask(operation);
        return this.mySubmit(task);
    }

    /**
     * Submits a Callable task for execution and returns a Future representing that task.
     * @param operation - the operation callable to submit.
     * @param type - TaskType that representing the priority.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Callable <T>operation,TaskType type){
        if (operation == null)
            throw new NullPointerException();
        Task<T> task = Task.createTask(operation,type);
        return mySubmit(task);
    }

    /**
     *Submits a Task <T> task for execution and returns a Future representing that task.
     * @param task - the task to submit.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> mySubmit(Task <T> task){
        arrOfPriority[task.getPriority()-1]++;
        if (this.maxPriority<task.getPriority())
            this.maxPriority=task.getPriority();
        execute(task);
        return task;
    }


    /**
     * Method invoked prior to executing the given Runnable in the given thread,
     * update the priority in the queue.
     * @param t - the thread that will run task r.
     * @param r - the task that will be executed.
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        for (int i=0 ;i<10;i++){
            if (arrOfPriority[i]>0){
                arrOfPriority[i]--;
                updateMaxPriority();
                break;
            }
        }
    }

    /**
     * Update the current max priority in the queue.
     */
    private void updateMaxPriority(){
        for (int i=9;i>=0;i--){
            if (arrOfPriority[i]>0){
                this.maxPriority=i+1;
                break;
            }else if (i==0&&arrOfPriority[i]==0){
                this.maxPriority=0;
            }
        }
    }

    /**
     * @return Current max priority in the queue.
     */
    public int getCurrentMax(){
        return this.maxPriority;
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
     */
    public void gracefullyTerminate(){
        try {
            this.shutdown();
            if (!this.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                this.shutdownNow();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
