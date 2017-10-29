package com.lib.collections.core.enums;

/**
 * Created by nikzz on 27/10/17.
 */
public enum MqReturnCode {
    /**
     * Timeout exception while processing
     */
    TIMEOUT,
    /**
     * Internal System Exception while processing
     * @help Contact nikhilmaheshwari46@gmail.com
     */
    SYSTEM,
    /**
     * Argument passed is not valid
     */
    INVALID_ARGUMENT,
    /**
     * Operation completed successfully
     */
    SUCCESS,
    /**
     * Failure while processing 
     */
    FAILURE
}
