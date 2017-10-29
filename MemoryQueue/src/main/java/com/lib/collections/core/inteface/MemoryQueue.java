package com.lib.collections.core.inteface;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MemoryQueue {
    /**
     * @return
     */
    MQWriteProcessor getMqWriter();

    /**
     * @param reader
     * @param name
     * @return
     */
    MQReadProcessor getMqReader(MQMessageReader reader, String name) throws InvalidArgumentException;

    /**
     * @return
     */
    MQSubscribeProcessor getMqSubscriber();
}
