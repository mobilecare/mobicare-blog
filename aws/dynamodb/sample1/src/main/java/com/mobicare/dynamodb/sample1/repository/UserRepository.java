package com.mobicare.dynamodb.sample1.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.mobicare.dynamodb.sample1.model.User;

@Repository
public class UserRepository {

	@Autowired
	private AmazonDynamoDBClient dbClient;

	private DynamoDBMapper dbMapper;

	@PostConstruct
	public void postConstruct() {
		dbMapper = new DynamoDBMapper(dbClient);
	}

	public void save(final User user) {
		dbMapper.save(user);
	}

	public void delete(final User user) {
		dbMapper.delete(user);
	}

	public List<User> getAll() {
		final ScanRequest scanRequest = new ScanRequest().withTableName(User.TABLE_NAME);
		final ScanResult result = dbClient.scan(scanRequest);
		return dbMapper.marshallIntoObjects(User.class, result.getItems());
	}

	public User getByEmail(final String email) {
		return dbMapper.load(User.class, email);
	}

	public List<User> searchByName(final String name) {

		final Condition scanFilterCondition = new Condition().withComparisonOperator(ComparisonOperator.CONTAINS).withAttributeValueList(
				new AttributeValue().withS(name));
		final Map<String, Condition> conditions = new HashMap<String, Condition>();
		conditions.put("name", scanFilterCondition);

		final ScanRequest scanRequest = new ScanRequest().withTableName(User.TABLE_NAME).withScanFilter(conditions);

		final ScanResult result = dbClient.scan(scanRequest);

		return dbMapper.marshallIntoObjects(User.class, result.getItems());

	}
}
