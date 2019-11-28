package com.yh.kafka.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Kafka 2.2.0 Java客户端开发——消息生产者
 *
 * Kafka消息生产者逻辑应当具备以下步骤：
 *       配置生产者参数，创造生产者实例KafkaProducer
 *      构建待发送的消息ProducerRecord
 *      发送消息
 *      程序退出或者无需生产消息时关闭生产者实例KafkaProducer
 *
 *
 *创建生产者实例KafkaProducer前需要指定相应的配置参数，配置参数需要存储在Properties中，有3个参数是必须要指定的：
 *
 * bootstrap.servers（ProducerConfig.BOOTSTRAP_SERVERS_CONFIG）：
 * 该参数指定了生产者客户端连接Kafka集群所需的Broker列表，格式为host:port，用逗号分隔。
 * 这里并非需要所有的Broker，生产者可以根据一个Broker来自动获取其它Broker的信息。
 * 建议设置为2个以上，当客户端连接的Broker因为网络故障或者该Broker宕机时可以根据该配置连接至其它Broker
 *
 * key.serializer和value.serializer
 * （ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG和ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG）：
 * 在传递给Broker时必须将消息ProducerRecord中的key和value转换为字节数组（byte[]）的形式，
 * 其转换逻辑由key.serializer和value.serializer实现，这两个参数必须是一个实现了Serializer接口的类的全限定名，
 * 且具备一个无参构造方法。
 *
 * 将上述参数存入Properties对象后，就可以构造生产者实例KafkaProducer了。

 */
public class Producer220 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 需求：向地址为 192.168.1.31:9092的 Broker 主题 topic-test 发送 100 条消息
         */
        //第一步：配置生产者参数
        Properties props = new Properties() ;
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "192.168.1.31:9092,192.168.1.32:9092,192.168.1.33:9092") ; //指定Kafka服务端地址
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName()) ;//指定key的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName()) ; //指定value的序列化方式
        props.put(ProducerConfig.ACKS_CONFIG , "all") ;
        props.put(ProducerConfig.CLIENT_ID_CONFIG , "producer.client.id.demo") ;
        //创建生产者实例
        KafkaProducer<String , String> producer = new KafkaProducer<String, String>(props  ) ;
        //
        for (int i = 0; i < 100; i++) {//发送100条消息
            //构建待发送的消息 ProducerRecord 。 一个ProducerRecord实例代表一个独立的消息
            ProducerRecord<String , String> record = new ProducerRecord<>("topic-test" , "message-"+i) ;
            //发送消息（RecordMetadata包含了消息的一些元数据，例如消息的主题、分区、分区中的偏移量、时间戳。）
            //RecordMetadata recordMetadata = producer.send(record).get(); //调用get()方法阻塞等待 Kafka 响应---同步发送
            //Future<RecordMetadata> send = producer.send(record); //不调用get()方法即代表异步发送，这个方法本身就是异步的
            producer.send(record);
            //数据打印
           // System.out.println(recordMetadata.topic() + " " + recordMetadata.partition() + " " + recordMetadata.offset());

        }
        //程序退出或者无需生产消息时关闭生产者实例 KafkaProducer
       //

        // 发现一个问题，程序运行完，但是consumer并没有消费到数据。必须让程序休眠一段时间，后才有消费。
        // kafka 消息不丢失机制。
        Thread.sleep(2000);

        producer.close();
    }
}
