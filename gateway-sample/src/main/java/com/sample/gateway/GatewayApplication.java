package com.sample.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@EnableBinding({ GatewayApplication.GatewayChannels.class, Processor.class })
@SpringBootApplication
public class GatewayApplication {

	public static final UUID instanceUUID = UUID.randomUUID();

	interface GatewayChannels {

		String REPLY_CHANNEL = "reply_channel";

		String REQUEST_CHANNEL = "request_channel";

		@Input(REPLY_CHANNEL)
    SubscribableChannel reply();

		@Output(REQUEST_CHANNEL)
    MessageChannel request();
	}

	@MessagingGateway
	public interface StreamGateway {
		@Gateway(requestChannel = GatewayChannels.REQUEST_CHANNEL, replyChannel = GatewayChannels.REPLY_CHANNEL)
		String process(String payload);
	}


	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	@IntegrationConverter
	public Converter<byte[], String> bytesToStringConverter() {
		return new Converter<byte[], String>() {
			@Override
			public String convert(byte[] source) {
				return new String(source);
			}
		};
	}

	@Bean
	public IntegrationFlow requestFlow() {
		return IntegrationFlows.from(GatewayChannels.REQUEST_CHANNEL)
			.enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
			.enrichHeaders(headerEnricherSpec -> headerEnricherSpec.header("instance_id",instanceUUID))
			.channel(GatewayChannels.REQUEST_CHANNEL)
			.get();
	}

	@Bean
	public IntegrationFlow replyFlow() {
		return IntegrationFlows.from(GatewayChannels.REPLY_CHANNEL)
			.filter(Message.class, message -> instanceUUID.equals(message.getHeaders().get("instance_id")))
			.channel(GatewayChannels.REPLY_CHANNEL)
			.get();
	}


	@RestController
	public class UppercaseController {

		@Autowired
		StreamGateway gateway;

		@GetMapping(value = "/string/{string}")
		public ResponseEntity<String> getUser(@PathVariable("string") String string) {
			return new ResponseEntity<String>(gateway.process(string), HttpStatus.OK);
		}
	}


	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Message<?> process(Message<String> request) {
		return MessageBuilder.withPayload(request.getPayload().toUpperCase())
				.copyHeaders(request.getHeaders())
				.build();
	}

}
