package com.lib.collections.business;

import com.lib.collections.core.classes.Message;
import com.lib.collections.core.inteface.MQSubscriptionReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by nikzz on 28/10/17.
 */
public class InternalMQSubscriber implements MQSubscriptionReader {

    private MQSubscriber subscriber;

    public InternalMQSubscriber(final MQSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void OnRead(Message message) {
        HashMap<String, ArrayList<MQSubscriptionReader>> subscriptionMap = subscriber.getSubscriptionMap();
        if(subscriptionMap.size() == 0){
            return;
        }

        Set<String> keys = subscriptionMap.keySet();
        String data = message.getMessage();
        for(String pattern : keys){
            System.out.println("Pattern is : "+pattern+", data : "+data);
            if(Pattern.matches(pattern, data)){
                ArrayList<MQSubscriptionReader> callbacks = subscriptionMap.get(pattern);
                for(MQSubscriptionReader callback : callbacks){
                    callback.OnRead(message);
                }
            }
        }

        return;
    }
}
