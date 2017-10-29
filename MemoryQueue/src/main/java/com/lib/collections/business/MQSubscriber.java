package com.lib.collections.business;

import com.lib.collections.core.inteface.MQSubscribeProcessor;
import com.lib.collections.core.inteface.MQSubscriptionReader;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;

/**
 * Created by nikzz on 28/10/17.
 */
public class MQSubscriber implements MQSubscribeProcessor {

    private HashMap<String,ArrayList<MQSubscriptionReader>> subscriptionMap;

    private static MQSubscriber mqSubscriber;

    private MQSubscriber(){
        subscriptionMap = new HashMap<>();
    }

    public static MQSubscriber getMqSubscriber(final com.lib.collections.queue.Queue queue){
        if(mqSubscriber == null){
            mqSubscriber = new MQSubscriber();
            MQWriter.getMqWriter(queue).attachSubscriber(new InternalMQSubscriber(mqSubscriber));
        }
        return mqSubscriber;
    }

    public HashMap<String, ArrayList<MQSubscriptionReader>> getSubscriptionMap() {
        return subscriptionMap;
    }

    public void subscribe(String pattern, MQSubscriptionReader callback) throws InvalidArgumentException {

        if(pattern == null || callback == null){
            String[] errorMessage = {"Pattern or callback can not be null."};
            throw new InvalidArgumentException(errorMessage);
        }

        ArrayList<MQSubscriptionReader> readers = subscriptionMap.get(pattern);
        if(readers == null){
            readers = new ArrayList<>();
        }
        readers.add(callback);
        subscriptionMap.put(pattern, readers);
    }
}
