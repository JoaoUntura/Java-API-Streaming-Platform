package dev.joaountura.streaming_project.consumers;

import dev.joaountura.streaming_project.producers.VideoTranscoderProducer;
import dev.joaountura.streaming_project.services.TranscodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VideoTranscoderConsumer {

    @Autowired
    private TranscodeService transcodeService;

    @KafkaListener(topics = VideoTranscoderProducer.topicVideoTranscoder, groupId =  "default_group")
    public void listenDefaultGroup(String fileId) throws IOException, InterruptedException {
        transcodeService.transcodeVideo(fileId);
    }

}
