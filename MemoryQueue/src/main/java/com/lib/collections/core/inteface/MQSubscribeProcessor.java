package com.lib.collections.core.inteface;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nikzz on 29/10/17.
 */
public interface MQSubscribeProcessor {
    HashMap<String, ArrayList<MQSubscriptionReader>> getSubscriptionMap();
    void subscribe(String pattern, MQSubscriptionReader callback) throws InvalidArgumentException;
}
