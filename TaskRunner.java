package edu.utdallas.taskExecutorImpl;
import edu.utdallas.blockingFIFO.*;

public class TaskRunner implements Runnable {

	private BlockingQueue blockingQueue;

	public TaskRunner(BlockingQueue blockingFifo) {
		this.blockingQueue = blockingFifo;
	}

	@Override
	public void run() {
		while (true) {
			try { 
				blockingQueue.take().execute();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}