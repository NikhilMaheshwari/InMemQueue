package com.lib.collections;

import com.lib.collections.business.MQReader;
import com.lib.collections.business.MQSubscriber;
import com.lib.collections.business.MQWriter;
import com.lib.collections.core.inteface.*;
import com.lib.collections.queue.Queue;
import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 27/10/17.
 */
public class InMemoryQueue implements MemoryQueue{

    private int size;
    private Queue queue;
    private static InMemoryQueue memQueue;

    /**
     * @param size
     */
    public InMemoryQueue(int size) throws InvalidArgumentException {
        if(size <= 0){
            String[] errorMessge = {"Size can not be null"};
            throw new InvalidArgumentException(errorMessge);
        }
        this.size = size;
        queue = new Queue(size);
    }

    /**
     * @return
     */
    public MQWriteProcessor getProducer() {
        return MQWriter.getMqWriter(queue);
    }

    /**
     * @param reader
     * @param name
     * @return
     */
    public MQReadProcessor getMqReader(MQMessageReader reader, String name){
        return new MQReader(queue, reader, true,name);
    }


    /**
     * @return
     */
    public MQSubscribeProcessor getMqSubscriber(){
        return MQSubscriber.getMqSubscriber(queue);
    }
}
