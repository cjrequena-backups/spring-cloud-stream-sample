package com.sample.springcloudstream.source.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

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
public interface KafkaChannel {

  String FOO_OUTPUT_CHANNEL1 = "foo_output_channel_1";

  @Output(FOO_OUTPUT_CHANNEL1)
  MessageChannel fooOutputChannel1();

}
