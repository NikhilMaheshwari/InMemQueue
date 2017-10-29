package com.lib.collections.core.inteface;

import com.lib.collections.core.classes.MQWriteResponse;
import com.lib.collections.core.enums.MQConnectionState;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MQWriteProcessor {
    MQConnectionState connect();
    boolean isConnected();
    String getId();
    MQWriteResponse WriteMessage(final String message);
}
