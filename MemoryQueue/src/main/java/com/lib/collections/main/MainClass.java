package com.lib.collections.main;

import com.lib.collections.InMemoryQueue;
import com.lib.collections.business.MQReader;
import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.enums.MqReturnCode;
import com.lib.collections.core.inteface.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 28/10/17.
 */
public class MainClass {
    public static void main(String[] args) throws InvalidArgumentException {
        MemoryQueue queue = new InMemoryQueue(100);

        final MQWriteProcessor producer = queue.getMqWriter();

        MQSubscribeProcessor subscriber = queue.getMqSubscriber();
        subscriber.subscribe("^[^0-9]*[12]?[0-9]{1,2}[^0-9]*", new SubscriptionListener());

        for(int i = 0;i<10;i++){
            final int index = i;
            Runnable runnable = () -> {
                for(int j = 0; j < 800; j++)
                   producer.WriteMessage(String.format("Adding Index "+index + " Value : "+ j));
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }

        /*for(int j = 0; j < 80000; j++)
            producer.WriteMessage(String.format("Adding Index "+j));*/

        final MQReadProcessor reader = queue.getMqReader(new QueueListener(), "Reader1");
        reader.connect();
        System.out.println("Reader is : "+reader.isConnected());
        Runnable readerRunnable = () -> {
            try {
                reader.startReading();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };

        Thread readerThread = new Thread(readerRunnable);
        readerThread.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reader.disconnect();

        System.out.print(producer.toString());
        return;
    }

}

class SubscriptionListener implements MQSubscriptionReader{

    @Override
    public void OnRead(Message message) {
        System.out.println("Subscription message received : "+message.toString());
    }
}
class QueueListener implements MQMessageReader {

    private int readCount = 0;
    private int temp = 1;

    @Override
    public MQReadAction OnRead(MQReader reader, Message message) {
        System.out.println("Message read : "+message.toString());
        if(temp % 2 == 0){
            temp++;
            System.out.println("Rollback for message : "+message.toString());
            return MQReadAction.ROLLBACK;
        }
        temp++;
        readCount++;
        System.out.println("Total Reads done : "+readCount);
        return MQReadAction.COMMIT;
    }

    @Override
    public void OnConnect(MQReader reader) {
        System.out.println("CONNECTED to MQ.");
    }

    @Override
    public void OnError(MQReader reader, MqReturnCode returnCode) {
        System.err.println("Erron in processing : "+ returnCode);
    }

    @Override
    public void OnDisconnect(MQReader reader) {
        System.out.println("DisConnected to MQ.");
    }
}
