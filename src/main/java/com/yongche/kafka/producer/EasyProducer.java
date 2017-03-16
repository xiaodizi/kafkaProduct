package com.yongche.kafka.producer;

import kafka.javaapi.producer.Producer;
import java.util.*;
import java.util.concurrent.TimeUnit;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

public class EasyProducer extends Thread {
	
	private String topic;
	
	public EasyProducer(String topic){
		super();
		this.topic = topic;
	}
	
	@Override
	public void run(){
		Producer producer = createProducer();
		int i = 1;
		while(true){
			producer.send(new KeyedMessage<Integer, String>(topic, "付磊很帅，我说了:"+(i++)+"次 o(∩_∩)o !"));
			try{
				TimeUnit.SECONDS.sleep(1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	
	private Producer createProducer(){
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "10.0.11.235:2181");   //多个用，分隔
		properties.put("serializer.class", StringEncoder.class.getName());
		properties.put("metadata.broker.list", "10.0.11.235:9092");  //多个用,分隔
		return new Producer<Integer, String>(new ProducerConfig(properties));
	}
	

	public static void main(String[] args) {
		new EasyProducer("xiaodizi").start();
	}

}
