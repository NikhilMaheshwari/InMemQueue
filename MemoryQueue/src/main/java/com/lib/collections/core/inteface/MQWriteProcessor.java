package com.lib.collections.core.inteface;

import com.lib.collections.core.classes.MQWriteResponse;
import com.lib.collections.core.enums.MQConnectionState;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MQWriteProcessor {
    /**
     * @return
     */
    MQConnectionState connect();

    /**
     * @return
     */
    boolean isConnected();

    /**
     * @return
     */
    String getId();

    /**
     * @param message
     * @return
     */
    MQWriteResponse WriteMessage(final String message);
}
