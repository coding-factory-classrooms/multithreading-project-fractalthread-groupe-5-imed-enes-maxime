package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    private final int nbThreadMax;
    private final HashMap<Integer,Runnable> queue;
    private final List<Thread> currentThreads;
    private boolean locked;
    private volatile AtomicInteger nextTaskNumber = new AtomicInteger(0);

    public ThreadPool(int nbThreadMax) {
        this.nbThreadMax = nbThreadMax;
        queue = new HashMap<>();
        currentThreads = new ArrayList<>();
    }

    public boolean Execute(Runnable task){

        if(locked){
            return false;
        }

        if(currentThreads.size() < nbThreadMax){
            Thread thread = new Thread(task);
            currentThreads.add(thread);
            thread.start();
        }else{
            queue.put(getNextTaskNumber(),task);
        }

        return true;
    }

    public void Lock(){
        locked = true;
    }

    private void FinishThread(Thread thread){
        currentThreads.remove(thread);
        StartNextTask();
    }

    private void StartNextTask(){
        //queue.get()
    }

    private Integer getNextTaskNumber(){
        return  nextTaskNumber.incrementAndGet();
    }

}
