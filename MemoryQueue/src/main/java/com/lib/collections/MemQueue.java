package com.lib.collections;

import com.lib.collections.business.MQReader;
import com.lib.collections.business.MQSubscriber;
import com.lib.collections.business.MQWriter;
import com.lib.collections.core.inteface.MQMessageReader;
import com.lib.collections.core.inteface.MQSubscriptionReader;
import com.lib.collections.queue.InMemQueue;
import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 27/10/17.
 */
public class MemQueue {

    private int size;
    private InMemQueue queue;
    private static MemQueue memQueue;

    /**
     * @param size
     */
    public MemQueue(int size) throws InvalidArgumentException {
        if(size <= 0){
            String[] errorMessge = {"Size can not be null"};
            throw new InvalidArgumentException(errorMessge);
        }
        this.size = size;
        queue = new InMemQueue(size);
    }

    /**
     * @return
     */
    public MQWriter getProducer() {
        return MQWriter.getMqWriter(queue);
    }

    /**
     * @param reader
     * @param name
     * @return
     */
    public MQReader getMqReader(MQMessageReader reader, String name){
        return new MQReader(queue, reader, true,name);
    }

    public MQSubscriber getMqSubscriber(){
        return MQSubscriber.getMqSubscriber(queue);
    }
}
