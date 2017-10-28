package com.lib.collections.queue;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nikzz on 27/10/17.
 */
public class InMemQueue  {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private String[] items;

    private int size;

    private int putptr;
    private int  takeptr;
    private int count;

    
    public int getCount() {
        return count;
    }

    public InMemQueue(final int size){
        this.size = size;
        items = new String[size];
        count = 0;
        putptr = 0;
        takeptr = 0;
    }

    public void put(String x) throws InterruptedException, InvalidArgumentException, TimeoutException {

        if(x == null){
            String[] errorMessage = {"Invalid input parameters. Null Not allowed"};
            throw new InvalidArgumentException(errorMessage);
        }

        lock.lock();

        try {
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    public String take() throws InterruptedException {

        lock.lock();

        try {
            while (count == 0)
                notEmpty.await();
            String x = items[takeptr];
            items[takeptr] = null;
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append(String.format("InMemQueue of size %d. \n", items.length));
        if(items.length == 0 ){
            response.toString();
        }

        for(String data : items ){
            if(data == null)
                break;
            response.append(data);
            response.append("\n");
        }
        return response.toString();
    }
}
