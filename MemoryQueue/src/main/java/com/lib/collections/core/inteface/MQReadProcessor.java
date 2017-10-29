package com.lib.collections.core.inteface;

import com.lib.collections.core.classes.ReaderCondition;
import com.lib.collections.core.enums.MQConnectionState;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MQReadProcessor {
    /**
     * @return
     */
    boolean isConnected();

    /**
     * @return
     */
    String getReaderName();

    /**
     * @return
     */
    MQConnectionState connect();

    /**
     * @return
     */
    MQConnectionState disconnect();

    /**
     *
     */
    default void setReadingEnabled() {

    }

    /**
     * @throws IllegalAccessException
     */
    void startReading() throws IllegalAccessException;


    /**
     * @param readerConditions
     * @throws InvalidArgumentException
     */
    void setReaderConditions(ArrayList<ReaderCondition> readerConditions) throws InvalidArgumentException;
}
