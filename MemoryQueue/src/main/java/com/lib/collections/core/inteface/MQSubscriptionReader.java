package com.lib.collections.core.inteface;

import com.lib.collections.core.classes.Message;

/**
 * Created by nikzz on 28/10/17.
 */
public interface MQSubscriptionReader {
    /**
     * @param message
     */
    void OnRead(Message message);
}
