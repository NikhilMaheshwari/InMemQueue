package com.lib.collections.business;

import com.lib.collections.core.classes.Message;
import com.lib.collections.core.enums.MQConnectionState;
import com.lib.collections.core.inteface.MQSubscriptionReader;
import com.lib.collections.core.inteface.MQWriteProcessor;
import com.lib.collections.queue.Queue;
import com.lib.collections.core.classes.MQWriteResponse;
import com.lib.collections.core.enums.MqReturnCode;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by nikzz on 27/10/17.
 */
public class MQWriter implements MQWriteProcessor {
    private String id;
    private Queue queue;

    private boolean isConnected;

    private MQSubscriptionReader subscriberReader;

    private static Logger logger = Logger.getLogger(MQWriter.class);

    private static MQWriter mqWriter;

    private MQWriter(Queue queue){
        this.queue = queue;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * @param queue
     * @return
     */
    public static MQWriter getMqWriter(final Queue queue) {
        if(mqWriter ==null){
            mqWriter = new MQWriter(queue);
        }
        return mqWriter;
    }

    protected void attachSubscriber(final MQSubscriptionReader subscriberReader){
        this.subscriberReader = subscriberReader;
    }

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
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    @Override
    public MQConnectionState connect(){
        if(!isConnected){
            isConnected = true;
        }
        return MQConnectionState.CONNECTED;
    }

    /**
     * @param message
     * @return
     */
    @Override
    public MQWriteResponse WriteMessage(final String message){
        MQWriteResponse response = new MQWriteResponse();

        try{
            logger.info("Write request from thread : "+Thread.currentThread().getId() + " with value : "+message);
            queue.put(message);
            SendToSubscriber(message);
            SetSuccessMessage(response);
            logger.info("Write success from thread : "+ Thread.currentThread().getId() + " with value : "+message);
        }
        catch(InterruptedException interruptedException){
            logger.error(interruptedException);
            SetErrorMessage(response, MqReturnCode.SYSTEM, interruptedException.getMessage());
        }
        catch (InvalidArgumentException invalidArgumentException){
            logger.error(invalidArgumentException);
            SetErrorMessage(response,MqReturnCode.INVALID_ARGUMENT, invalidArgumentException.getMessage());
        }
        catch (TimeoutException timeoutException){
            logger.error(timeoutException);
            SetErrorMessage(response,MqReturnCode.TIMEOUT, timeoutException.getMessage());
        }
        catch (Exception exception){
            logger.error(exception);
            SetErrorMessage(response,MqReturnCode.FAILURE, exception.getMessage());
        }

        return response;
    }

    private void SendToSubscriber(String message){
        if(subscriberReader == null){
            return;
        }
        String messageId = UUID.randomUUID().toString();
        Message subsMessage = new Message(messageId, message, new Date());
        subscriberReader.OnRead(subsMessage);
    }

    private void SetErrorMessage(MQWriteResponse response, MqReturnCode returnCode, String errorMessage){
        response.setSuccessful(false);
        response.setReturnCode(returnCode);
        response.setErrorMessage(errorMessage);
    }

    private void SetSuccessMessage(MQWriteResponse response){
        response.setReturnCode(MqReturnCode.SUCCESS);
        response.setSuccessful(true);
    }

    @Override
    public String toString() {
        return "MQWriter Queue Size : "+queue.getCount() + " and Data : \n"+queue.toString();
    }
}
