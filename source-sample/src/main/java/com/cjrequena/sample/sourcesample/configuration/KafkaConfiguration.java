package com.cjrequena.sample.sourcesample.configuration;

import com.cjrequena.sample.sourcesample.channel.KafkaChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * @author cjrequena
 * @version 1.0
 * @since JDK1.8
 * @see
 *
 */
@Configuration
@EnableBinding(KafkaChannel.class)
public class KafkaConfiguration {
}
