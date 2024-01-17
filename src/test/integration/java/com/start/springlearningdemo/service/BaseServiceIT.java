package com.start.springlearningdemo.service;

import com.start.springlearningdemo.SpringLearningDemoApplication;
import com.start.springlearningdemo.service.file.FileService;
import com.start.springlearningdemo.utils.TestFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringLearningDemoApplication.class)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
@Slf4j
public class BaseServiceIT {

  @Autowired protected FileService fileService;

  protected TestFileReader testFileReader = new TestFileReader();

  @MockBean protected KafkaTemplate<String, String> kafkaTemplate;

  @MockBean protected ConsumerFactory<String, String> consumerFactory;
}
