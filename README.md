Welcome to the **InMemQueue** wiki!

InMemQueue is an Array based In memory queue to store String messages

Features 
- One Producer and N Consumers for Read and Write to Queue
- Queue is size bound and completely in-memory. (Size is configurable)
- Handle concurrent writes and reads consistently between producer and consumers
- Provide retry mechanism to handle failures in message processing.
- Allow subscription of Consumers to messages that match a particular expression

[JavaDoc](https://nikhilmaheshwari.github.io/InMemQueue/MemoryQueue/target/site/apidocs/)

# Code Samples
* Initialiser
```Java    
    int size = 100;
    MemoryQueue queue = new InMemoryQueue(size);
```
* MQ Writer
```Java    
    final MQWriteProcessor producer = queue.getMqWriter();
    for(int j = 0; j < 800; j++)
                   producer.WriteMessage(String.format("Adding Index "+index + " Value : "+ j));
```
* MQ Reader on same thread
```Java
    final MQReadProcessor reader = queue.getMqReader(new QueueListener(), "Reader1");
    reader.connect();
    System.out.println("Reader is : "+reader.isConnected());
    reader.startReading();
```
* MQ Reader on new thread
```Java
    final MQReadProcessor reader = queue.getMqReader(new QueueListener(), "Reader1");
    reader.connect();
    System.out.println("Reader is : "+reader.isConnected());
    Runnable readerRunnable = () -> {
        try {
            reader.startReading();
            Thread.sleep(30000);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    Thread readerThread = new Thread(readerRunnable);
    readerThread.start();

    
```
   1. Implementation of Listener (MQMessageReader) to read on any new message 
```Java
class QueueListener implements MQMessageReader {

    /*
    * In case of succcessful processing, return MQReadAction.COMMIT;
    * In case of error in processing, return MQReadAction.ROLLBACK
    * In case of ROLLBACK, message will be put back to queue
    */
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
```
* MQ Subscriber
```Java
    MQSubscribeProcessor subscriber = queue.getMqSubscriber();
    subscriber.subscribe("^[^0-9]*[12]?[0-9]{1,2}[^0-9]*", new SubscriptionListener());
```
    1. Implementation of Listener (MQSubscriptionReader) to for subscriber

```Java
class SubscriptionListener implements MQSubscriptionReader{

    @Override
    public void OnRead(Message message) {
        System.out.println("Subscription message received : "+message.toString());
    }
}

```
