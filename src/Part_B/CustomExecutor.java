package Part_B;

import java.util.concurrent.*;

public class CustomExecutor<T> extends ThreadPoolExecutor {

    private int maxPriority;

    public CustomExecutor(){
        super(Runtime.getRuntime().availableProcessors()/2,Runtime.getRuntime().availableProcessors()-1,300, TimeUnit.MILLISECONDS,new PriorityBlockingQueue<>());
        this.maxPriority=0;
    }

    public <T> Future<T> submit(Task <T> task){
        if (task == null)
            throw new NullPointerException();
        maxPriority= Math.max(task.getPriority(), maxPriority);
        execute(task);
        return task;
    }

    public <T> Future<T> submit(Callable <T> operation){
        if (operation == null)
            throw new NullPointerException();
        Task<T> task = new Task<T>(operation, 3);
        return submit(task);
    }

    public <T> Future<T> submit(Callable <T>operation,TaskType type){
        if (operation == null)
            throw new NullPointerException();
        Task<T> task = new Task<T>(operation, type.getPriorityValue());
        return submit(task);
    }
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t,r);
        if (this.getQueue().isEmpty()){
            maxPriority=0;
        }else if (getCurrentMax()>maxPriority){
            maxPriority=getCurrentMax();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (this.getQueue().isEmpty()){
            maxPriority=0;
        }else if (getCurrentMax()>maxPriority){
            maxPriority=getCurrentMax();
        }
    }

    public int getCurrentMax(){
        return this.maxPriority;
    }
    public void gracefullyTerminate(){
        this.shutdown();
    }

}