package com.yongche.kafka.producer;

import java.awt.List;
import java.util.ArrayList;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import scala.xml.Null;

public class KafkaProducer implements Runnable {

	private Producer<String, String> producer = null;

	private ProducerConfig config = null;

	public KafkaProducer() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "10.0.11.235:2181");
		// 指定序列化处理类，默认为kafka.serializer.DefaultEncoder,即byte[]
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// 同步还是异步，默认2表同步，1表异步。异步可以提高发送吞吐量，但是也可能导致丢失未发送过去的消息
		props.put("producer.type", "sync");
		// 是否压缩，默认0表示不压缩，1表示用gzip压缩，2表示用snappy压缩。压缩后消息中会有头来指明消息压缩类型，故在消费者端消息解压是透明的无需指定。
		props.put("compression.codec", "1");
		// 指定kafka节点列表，用于获取metadata(元数据)，不必全部指定
		props.put("metadata.broker.list", "10.0.11.235:9092");
		config = new ProducerConfig(props);
	}

	public void run() {
		producer = new Producer<String, String>(config);
		while (true) {
			for (int i = 1; i <= 6; i++) { // 向6个分区发送数据
				java.util.List<KeyedMessage<String, String>> messagelist = new ArrayList<KeyedMessage<String, String>>();
				for (int j = 0; j < 6; j++) { // 每个分区发送6条数据
					messagelist.add(new KeyedMessage<String, String>("xiaodizi", "在分区" + i + "；付磊很帅，美女说的: o(∩_∩)o !"));
				}
				producer.send(messagelist);
			}
		}
	}

	public static void main(String[] args) {
		Thread t = new Thread(new KafkaProducer());
		t.start();
	}

}
