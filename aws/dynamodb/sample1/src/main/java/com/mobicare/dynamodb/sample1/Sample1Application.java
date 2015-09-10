package com.mobicare.dynamodb.sample1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@SpringBootApplication
public class Sample1Application {
	private static final String ACCESS_KEY = "AKIAIWA25KGQEMDT74SA";
	private static final String SECRET_KEY = "+HNGLS85Noa/l4krqjuEYlUtB4fCvpbCXN9wdMkz";

	public static void main(final String[] args) {
		SpringApplication.run(Sample1Application.class, args);
	}

	@Bean
	public AmazonDynamoDBClient amazonDynamoDBClient() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		return new AmazonDynamoDBClient(basicAWSCredentials);
	}
}
