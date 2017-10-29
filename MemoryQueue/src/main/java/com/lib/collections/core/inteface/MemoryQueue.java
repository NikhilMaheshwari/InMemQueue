package com.lib.collections.core.inteface;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MemoryQueue {
    MQWriteProcessor getProducer();
    MQReadProcessor getMqReader(MQMessageReader reader, String name);
    MQSubscribeProcessor getMqSubscriber();
}
