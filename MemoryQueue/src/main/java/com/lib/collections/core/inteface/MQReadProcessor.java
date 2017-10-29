package com.lib.collections.core.inteface;

import com.lib.collections.core.enums.MQConnectionState;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MQReadProcessor {
    boolean isConnected();
    String getReaderName();
    MQConnectionState connect();
    MQConnectionState disconnect();
    void setReadingEnabled();
    void startReading() throws IllegalAccessException;
}
