package dev.joaountura.streaming_project.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class VideoTranscoderProducer {

    public final static String topicVideoTranscoder = "videos-to-process";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendVideo(String fileName) {
        kafkaTemplate.send(topicVideoTranscoder, fileName);
    }

}
