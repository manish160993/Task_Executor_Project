package edu.utdallas.taskExecutorImpl;
import edu.utdallas.blockingFIFO.*;
import edu.utdallas.taskExecutor.*;

public class TaskExecutorImpl implements TaskExecutor
{
	private int threadPoolSize;
	private TaskRunner threadPool[];
	private BlockingQueue blockingQueue;
	
	// Public method to declare thread pool size
	public TaskExecutorImpl(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
		createThreadPool();
	}
	
	// Method to create the thread pool
	private void createThreadPool() {
		this.blockingQueue = new BlockingQueue(100);
		this.threadPool = new TaskRunner[threadPoolSize];

		for(int i = 0; i < threadPoolSize; i++) {
			String name = "TaskThread" + i;

			TaskRunner executingTask = new TaskRunner(this.blockingQueue);
			threadPool[i] = executingTask;

			Thread executingThread = new Thread(executingTask);
			executingThread.setName(name);
			executingThread.start();

			Thread.yield();
		}
	}
	
	// Method override to add a task to the blocking queue
	@Override
	public void addTask(Task task)
	{
		this.blockingQueue.append(task);
	}

}
