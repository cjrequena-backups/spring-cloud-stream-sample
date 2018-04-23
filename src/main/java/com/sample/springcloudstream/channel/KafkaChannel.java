package com.sample.springcloudstream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannel {

  String FOO_INPUT_CHANNEL1 = "foo_input_channel_1";
  String FOO_INPUT_CHANNEL2 = "foo_input_channel_2";
  String FOO_OUTPUT_CHANNEL1 = "foo_output_channel_1";
  String FOO_OUTPUT_CHANNEL2 = "foo_output_channel_2";

  @Input(FOO_INPUT_CHANNEL1)
  MessageChannel fooInputChannel1();

  @Input(FOO_INPUT_CHANNEL2)
  MessageChannel fooInputChannel2();

  @Output(FOO_OUTPUT_CHANNEL1)
  MessageChannel fooOutputChannel1();

  @Output(FOO_OUTPUT_CHANNEL2)
  MessageChannel fooOutputChannel2();


}
