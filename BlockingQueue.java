package edu.utdallas.blockingFIFO;
import edu.utdallas.taskExecutor.*;

public class BlockingQueue {
	
	Task blockingQueue[];
	int maxSize;
	int nextIn; 
	int nextOut;
	int count;
	Object notFull, notEmpty;

	// Constructor to initalize queue size
	public BlockingQueue(int blockingQueueSize) {
		maxSize = blockingQueueSize;
		blockingQueue = new Task[maxSize];
		nextIn = 0; 
		nextOut = 0;
		count = 0;
		notFull = new Object(); 
		notEmpty = new Object();
	}

	// Add a new task to the blockingQueue
	public void append(Task task) {

		if (count == maxSize) {

			// Buffer is full, avoid overflow
			synchronized(notFull) {
				try {
					notFull.wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		blockingQueue[nextIn] = task;
		nextIn = (nextIn + 1) % maxSize;
		count++;

		// Resume waiting threads
		synchronized(notEmpty) {
			notEmpty.notify();
		}
	}

	// Removes the next thread from the blockingQueue
	public Task take() {

		// Queue is empty, avoid underflow
		if (count == 0) {
			synchronized(notEmpty) {
				try {
					notEmpty.wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		Task task = blockingQueue[nextOut];
		nextOut = ((nextOut + 1) % maxSize);
		count--;

		// Resume any waiting threads
		synchronized(notFull) {
			notFull.notify();
		}

		return task;
	}

}