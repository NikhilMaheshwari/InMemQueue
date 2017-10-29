package com.lib.collections.core.enums;

/**
 * Created by nikzz on 28/10/17.
 */
public enum MQReadAction {
    /**
     *
     * Message has been processed successfully
     */
    COMMIT,
    /**
     * Failure while processing message and Push back to Queue
     */
    ROLLBACK
}
