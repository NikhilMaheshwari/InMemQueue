package com.lib.collections.core.inteface;

import com.lib.collections.business.MQReader;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MqReturnCode;

/**
 * Created by nikzz on 28/10/17.
 */
public interface MQMessageReader {
    /**
     * @param reader
     * @param message
     * @return
     */
    MQReadAction OnRead(MQReader reader,Message message);

    /**
     * @param reader
     */
    default void OnConnect(MQReader reader){}

    /**
     * @param reader
     * @param returnCode
     */
    default void OnError(MQReader reader, MqReturnCode returnCode){}

    /**
     * @param reader
     */
    default void OnDisconnect(MQReader reader){}
}
