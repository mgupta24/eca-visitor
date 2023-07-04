package com.eca.visitor.service.notification;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VisitorKafkaNotificationServiceTest {
	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;

	@InjectMocks
	private VisitorNotificationService visitorKafkaNotificationService;

	@Mock
	private ListenableFutureCallback<SendResult<String, String>> mockProducerCallback;

	@Test
	void kafkaNotificationTest() {
		ReflectionTestUtils.setField(visitorKafkaNotificationService, "kafkaEnabled", true);
		ReflectionTestUtils.setField(visitorKafkaNotificationService, "topicName", "test");
		ListenableFuture listenableFuture = mock(ListenableFuture.class);
		SendResult sendResult = mock(SendResult.class);
		RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("test", 0), 1L, 0, 0L, 0, 0);
		when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);
		when(kafkaTemplate.send(eq("test"),anyString())).thenReturn(listenableFuture);
		visitorKafkaNotificationService.sendNotification("test-message");
		verify(kafkaTemplate,times(1)).send(anyString(),eq("test-message"));
	}

	@Test
	void kafkaNotificationNoCallTest() {
		ReflectionTestUtils.setField(visitorKafkaNotificationService, "kafkaEnabled", false);
		ReflectionTestUtils.setField(visitorKafkaNotificationService, "topicName", "test");
		visitorKafkaNotificationService.sendNotification("test-message");
		verify(kafkaTemplate,times(0)).send(anyString(),eq("test"));
	}
}
