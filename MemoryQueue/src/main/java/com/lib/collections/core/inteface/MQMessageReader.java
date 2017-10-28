package com.lib.collections.core.inteface;

import com.lib.collections.business.MQReader;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MqReturnCode;

/**
 * Created by nikzz on 28/10/17.
 */
public interface MQMessageReader {
    MQReadAction OnRead(MQReader reader,Message message);

    default void OnConnect(MQReader reader){}

    default void OnError(MQReader reader, MqReturnCode returnCode){}

    default void OnDisconnect(MQReader reader){}
}
