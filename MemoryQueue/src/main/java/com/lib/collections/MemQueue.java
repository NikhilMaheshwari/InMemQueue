package com.lib.collections;

import com.lib.collections.business.MQReader;
import com.lib.collections.business.MQSubscriber;
import com.lib.collections.business.MQWriter;
import com.lib.collections.core.inteface.*;
import com.lib.collections.queue.InMemQueue;
import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 27/10/17.
 */
public class MemQueue implements MemoryQueue{

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

    public MQSubscribeProcessor getMqSubscriber(){
        return MQSubscriber.getMqSubscriber(queue);
    }
}
