package com.lib.collections.business;

import com.lib.collections.core.classes.Message;
import com.lib.collections.core.classes.ReaderCondition;
import com.lib.collections.core.enums.MQConnectionState;
import com.lib.collections.core.enums.MQReadAction;
import com.lib.collections.core.enums.MqReturnCode;
import com.lib.collections.core.inteface.MQMessageReader;
import com.lib.collections.core.inteface.MQReadProcessor;
import com.lib.collections.queue.Queue;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by nikzz on 28/10/17.
 */
public class MQReader implements MQReadProcessor{

    private Queue queue;

    private boolean isReadingEnabled;

    private boolean isReading;

    private String readerName;

    private boolean isConnected;

    private MQMessageReader messageReader;

    private ArrayList<ReaderCondition> readerConditions;

    private static Logger logger = Logger.getLogger(MQReader.class);

    /**
     * @return
     */
    @Override
    public boolean isConnected() {
        return isConnected;
    }


    /**
     * @return
     */
    @Override
    public String getReaderName() {
        return readerName;
    }


    /**
     * @param queue
     * @param reader
     * @param isReadingEnabled
     */
    public MQReader(final Queue queue, final MQMessageReader reader, final boolean isReadingEnabled) throws InvalidArgumentException{
        this(queue, reader,isReadingEnabled,null);
    }

    /**
     * @param queue
     * @param reader
     * @param isReadingEnabled
     * @param name
     */
    public MQReader(final Queue queue, final MQMessageReader reader, final boolean isReadingEnabled , final String name) throws InvalidArgumentException {
        this(queue, reader, isReadingEnabled, name, null);
    }

    public MQReader(final Queue queue, final MQMessageReader reader, final boolean isReadingEnabled , final String name, ArrayList<ReaderCondition> readerConditions) throws InvalidArgumentException {
        if(name == null){
            String[] errorMessage = {"Reader Name can not be null for Defining the readerCondition."};
            throw new InvalidArgumentException(errorMessage);
        }

        this.queue = queue;
        this.readerName = name;
        this.isReadingEnabled = isReadingEnabled;
        this.messageReader = reader;
        this.readerConditions = readerConditions;
    }

    /**
     * @param readerConditions
     */
    public void setReaderConditions(ArrayList<ReaderCondition> readerConditions) throws InvalidArgumentException {
        if(getReaderName() == null){
            String[] errorMessage = {"Reader Name can not be null for Defining the readerCondition."};
            throw new InvalidArgumentException(errorMessage);
        }
        this.readerConditions = readerConditions;
    }

    /**
     * @return
     */
    public ArrayList<ReaderCondition> getReaderConditions() {
        return readerConditions;
    }

    /**
     * @return
     */
    @Override
    public MQConnectionState connect(){
        if(!isConnected){
            isConnected = true;
        }

        messageReader.OnConnect(this);
        return MQConnectionState.CONNECTED;
    }

    /**
     * @return
     */
    @Override
    public MQConnectionState disconnect(){
        if(isConnected){
            isConnected = false;
            isReading = false;
        }
        messageReader.OnDisconnect(this);
        return MQConnectionState.DISCONNECTED;
    }

    /**
     *
     */
    @Override
    public void setReadingEnabled(){
        if(isConnected)
            this.isReadingEnabled = true;
        else
            throw new IllegalStateException("Reader is not connected.");
    }

    /**
     * @throws IllegalAccessException
     */
    @Override
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
