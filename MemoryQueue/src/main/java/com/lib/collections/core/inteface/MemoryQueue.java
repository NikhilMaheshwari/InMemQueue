package com.lib.collections.core.inteface;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MemoryQueue {
    /**
     * @return
     */
    MQWriteProcessor getProducer();

    /**
     * @param reader
     * @param name
     * @return
     */
    MQReadProcessor getMqReader(MQMessageReader reader, String name);

    /**
     * @return
     */
    MQSubscribeProcessor getMqSubscriber();
}
