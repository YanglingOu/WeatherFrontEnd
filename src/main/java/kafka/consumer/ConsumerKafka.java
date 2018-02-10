package kafka.consumer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import java.util.Date;

public class ConsumerKafka {
	private static String[] topics = {"WeatherDataLevel-100", "WeatherDataLevel-200", "WeatherDataLevel-300"};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int brokerId = Integer.valueOf(args[1]);
		String serverName = args[0];
		
		//Configure Kafka consumer
		Properties properties= new Properties();
		properties.put("bootstrap.servers", serverName);
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("group.id", "group1");
		properties.put("auto.offset.reset", "earliest");
		properties.put("enable.auto.commit", true);
		final KafkaConsumer<String, String> myConsumer = new KafkaConsumer<String, String>(properties);
	    myConsumer.subscribe(Arrays.asList(topics[brokerId]));
	    
  
	    
	    while (true) {
	    	// Write to redis server
		    RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
		    StatefulRedisConnection<String, String> redisConn = redisClient.connect();
		    RedisCommands<String, String> syncCommands =  redisConn.sync();
		    
	         ConsumerRecords<String, String> records = myConsumer.poll(10);
	         for (ConsumerRecord<String, String> record : records){
	        	 //read value of sonicTemprature;
	        	 //ObjectMapper mapperOne = new ObjectMapper();
	        	// DataModel dm;
	        	 try{
	        		// dm =mapperOne.readValue(record.value(), DataModel.class);
	        		 syncCommands.publish("weather", record.value());
	        		 
	        	 }catch (Exception e){
	        		 System.out.printf(e.getMessage());
	        	 };
	         }
	         //for (String s: syncCommands.lrange("weather", 0, 10)){
	        	// System.out.printf(s);
	         //}
	         
	         redisConn.close();
	         redisClient.shutdown();  
	     }
		
		
	}

}
