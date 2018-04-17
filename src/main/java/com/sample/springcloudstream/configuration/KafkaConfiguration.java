package com.sample.springcloudstream.configuration;

import com.sample.springcloudstream.channel.KafkaChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBinding(KafkaChannel.class)
public class KafkaConfiguration {
}
