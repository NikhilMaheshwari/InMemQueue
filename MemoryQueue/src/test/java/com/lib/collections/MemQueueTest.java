package com.lib.collections;

import com.lib.collections.business.MQReader;
import com.lib.collections.business.MQWriter;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MqReturnCode;
import com.lib.collections.core.inteface.MQMessageReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nikzz on 28/10/17.
 */
public class MemQueueTest {

    private MemQueue queue;

    private int size = 1000;

    @Before
    public void setUp() throws Exception {
        queue = new MemQueue(size);
        org.apache.log4j.BasicConfigurator.configure();
    }

    @Test
    public void Test_Write_To_Mq() throws Exception{
        final MQWriter producer = queue.getProducer();


        for(int j = 0; j < 10; j++)
            producer.WriteMessage(String.format("Adding Index "+j ));
        /*for(int i = 0;i<1;i++){
            final int index = i;
            Runnable runnable = new Runnable() {
                public void run() {
                    for(int j = 0; j < 10; j++)
                       producer.WriteMessage(String.format("Adding Index "+index + " Value : "+ j));
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }*/

        MQReader reader = queue.getMqReader(new QueueListener(), "Reader1");
        reader.connect();
        System.out.println("Reader is : "+reader.isConnected());
        reader.startReading();
        Thread.sleep(5);
        reader.disconnect();

        //SYSTEM.out.print(producer.toString());
    }

    class QueueListener implements MQMessageReader{

        @Override
        public MQReadAction OnRead(MQReader reader, Message message) {
            System.out.println("Message read : "+message.toString());
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
}