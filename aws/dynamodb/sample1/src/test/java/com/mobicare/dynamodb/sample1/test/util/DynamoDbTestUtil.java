package com.mobicare.dynamodb.sample1.test.util;

import java.util.Date;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.mobicare.dynamodb.sample1.model.User;

public final class DynamoDbTestUtil {

	private static final String ACCESS_KEY = "MY_ACCESS_KEY";
	private static final String SECRET_KEY = "MY_SECRET_KEY";

	private DynamoDbTestUtil() {
	}

	private static AmazonDynamoDBClient dbClient;

	public static void configureDynamoDB() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		dbClient = new AmazonDynamoDBClient(basicAWSCredentials);

		final DynamoDBMapper dbMapper = new DynamoDBMapper(dbClient);

		final CreateTableRequest createTableRequest = dbMapper.generateCreateTableRequest(User.class);
		createTableRequest.setTableName(User.TABLE_NAME);
		createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

		dbClient.createTable(createTableRequest);

		String tableStatus = "";
		do {
			try {
				Thread.sleep(1000);
			} catch (final Exception e) {
				e.printStackTrace();
			}
			final DescribeTableResult describeTable = dbClient.describeTable(User.TABLE_NAME);
			tableStatus = describeTable.getTable().getTableStatus();
		} while (!tableStatus.equals("ACTIVE"));

		final User user1 = new User();
		user1.setEmail("teste123@mobicare.com.br");
		user1.setName("teste 123 456");
		user1.setDate(new Date());
		dbMapper.save(user1);

		final User deleteUser = new User();
		deleteUser.setEmail("deleteUser@mobicare.com.br");
		deleteUser.setName("deleteUser");
		deleteUser.setDate(new Date());
		dbMapper.save(deleteUser);
	}

	public static void deleteTableUser() {
		dbClient.deleteTable(User.TABLE_NAME);
	}

}
