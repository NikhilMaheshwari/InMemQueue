package com.lib.collections;

import com.lib.collections.queue.InMemQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by nikzz on 27/10/17.
 */
public class InMemQueueTest {

    private InMemQueue queue;

    @Before
    public void setUp() throws Exception {
        queue = new InMemQueue(100);
    }

    @Test
    public void put_single_thread() throws Exception {
        for(int i=0;i<100;i++){
            queue.put(new Date().toString());
            System.out.println(queue.toString());
        }
    }

    @Test
    public void take_single_thread() throws Exception {
    }

}