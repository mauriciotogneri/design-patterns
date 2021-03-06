package com.mauriciotogneri.designpatterns.concurrency.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool
{
    private final Object threadPoolLock = new Object();
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private final List<WorkerThread> workerThreadsList = new ArrayList<>();

    public ThreadPool(int poolSize)
    {
        for (int i = 0; i < poolSize; i++)
        {
            WorkerThread thread = new WorkerThread(i, this);
            this.workerThreadsList.add(thread);
            thread.start();
        }
    }

    public void addTask(Runnable task)
    {
        synchronized (this.taskQueue)
        {
            this.taskQueue.add(task);

            synchronized (this.threadPoolLock)
            {
                this.threadPoolLock.notifyAll();
            }
        }
    }

    public boolean hasMoreTasks()
    {
        boolean result;

        synchronized (this.taskQueue)
        {
            result = (!this.taskQueue.isEmpty());
        }

        return result;
    }

    public synchronized Runnable getNextTask()
    {
        Runnable result = null;

        synchronized (this.taskQueue)
        {
            if (hasMoreTasks())
            {
                result = this.taskQueue.poll();
            }
        }

        return result;
    }

    public Object getLock()
    {
        return this.threadPoolLock;
    }

    public void shutdown()
    {
        for (WorkerThread thread : this.workerThreadsList)
        {
            thread.shutdown();
        }

        synchronized (this.threadPoolLock)
        {
            this.threadPoolLock.notifyAll();
        }
    }
}