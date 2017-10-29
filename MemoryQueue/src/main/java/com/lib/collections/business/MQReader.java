package com.lib.collections.business;

import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MQConnectionState;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.enums.MqReturnCode;
import com.lib.collections.core.inteface.MQMessageReader;
import com.lib.collections.core.inteface.MQReadProcessor;
import com.lib.collections.queue.InMemQueue;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by nikzz on 28/10/17.
 */
public class MQReader implements MQReadProcessor{

    private InMemQueue queue;

    private boolean isReadingEnabled;

    private boolean isReading;

    private String readerName;

    private boolean isConnected;

    private MQMessageReader messageReader;

    private static Logger logger = Logger.getLogger(MQReader.class);

    public boolean isConnected() {
        return isConnected;
    }

    public String getReaderName() {
        return readerName;
    }

    public MQReader(final InMemQueue queue, final MQMessageReader reader, final boolean isReadingEnabled){
        this(queue, reader,isReadingEnabled,null);
    }

    public MQReader(final InMemQueue queue, final MQMessageReader reader,final boolean isReadingEnabled ,final String name){
        this.queue = queue;
        this.readerName = name;
        this.isReadingEnabled = isReadingEnabled;
        this.messageReader = reader;
    }

    public MQConnectionState connect(){
        if(!isConnected){
            isConnected = true;
        }

        messageReader.OnConnect(this);
        return MQConnectionState.CONNECTED;
    }

    public MQConnectionState disconnect(){
        if(isConnected){
            isConnected = false;
            isReading = false;
        }
        messageReader.OnDisconnect(this);
        return MQConnectionState.DISCONNECTED;
    }

    public void setReadingEnabled(){
        if(isConnected)
            this.isReadingEnabled = true;
        else
            throw new IllegalStateException("Reader is not connected.");
    }

    public void startReading() throws IllegalAccessException {
        if(!isConnected){
            throw new IllegalStateException("Reader is not connected.");
        }

        if(!isReadingEnabled){
            throw new IllegalAccessException("Read is not enabled for reader.");
        }
        isReading = true;

        read();
    }

    private void read() {
        try {
            while (isReading) {
                final String data = queue.take();
                String messageId = UUID.randomUUID().toString();
                Date createdOn = new Date();
                Message message = new Message(messageId, data, createdOn);

                MQReadAction action = messageReader.OnRead(this, message);
                if(action == MQReadAction.COMMIT){
                    continue;
                }
                /*MQWriter writer = MQWriter.getMqWriter(queue);
                writer.connect();
                writer.WriteMessage(data);*/
                Thread thread = new Thread(() -> {
                    try {
                        queue.put(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        }
        catch(InterruptedException interruptedException){
            logger.error(interruptedException);
            messageReader.OnError(this, MqReturnCode.FAILURE);
            disconnect();
        }
        catch (Exception exception){
            logger.error(exception);
            messageReader.OnError(this, MqReturnCode.SYSTEM);
            disconnect();
        }

    }
}
