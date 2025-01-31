package configs;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import webmarket.core.ProductDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka settings, for sending messages, as well as a listener for receiving.
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String server;

    /**
     * Map of producer settings properties.
     * @return
     */
    @Bean
    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        return props;
    }

    /**
     * Deserializer of message objects.
     * @return
     */
    @Bean
    public JsonDeserializer jsonDeserializer(){
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        jsonDeserializer.addTrustedPackages("*");
        return jsonDeserializer;
    }

    /**
     * A map of the properties of the consumer settings.
     * @return Map
     */
    @Bean
    public Map<String, Object> consumerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return props;
    }

    /**
     * Consumer factory.
     * @return
     */
    @Bean
    public ConsumerFactory<Long, ProductDto> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * Listener's factory.
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Long, ProductDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Producer's factory.
     * @return
     */
    @Bean
    public ProducerFactory<Long, ProductDto> producerFactory(){
      return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Kafka template for sending messages.
     * @return
     */
    @Bean(value = "KafkaCart")
    public KafkaTemplate<Long, ProductDto> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
