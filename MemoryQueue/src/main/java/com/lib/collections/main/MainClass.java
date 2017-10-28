package com.lib.collections.main;

import com.lib.collections.MemQueue;
import com.lib.collections.business.MQReader;
import com.lib.collections.business.MQWriter;
import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.enums.MqReturnCode;
import com.lib.collections.core.inteface.MQMessageReader;
import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by nikzz on 28/10/17.
 */
public class MainClass {
    public static void main(String[] args) throws InvalidArgumentException {
        MemQueue queue = new MemQueue(1000);
        org.apache.log4j.BasicConfigurator.configure();

        final MQWriter producer = queue.getProducer();


        for(int i = 0;i<10;i++){
            final int index = i;
            Runnable runnable = () -> {
                for(int j = 0; j < 80; j++)
                   producer.WriteMessage(String.format("Adding Index "+index + " Value : "+ j));
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }

        MQReader reader = queue.getMqReader(new QueueListener(), "Reader1");
        try {
            reader.connect();
            System.out.println("Reader is : "+reader.isConnected());
            reader.startReading();
            Thread.sleep(30000);
            reader.disconnect();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(producer.toString());
        return;
    }

}
class QueueListener implements MQMessageReader {

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
