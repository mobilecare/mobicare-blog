package com.mobicare.dynamodb.sample1;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mobicare.dynamodb.sample1.model.User;
import com.mobicare.dynamodb.sample1.repository.UserRepository;
import com.mobicare.dynamodb.sample1.test.util.DynamoDbTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Sample1Application.class)
@WebAppConfiguration
public class UserTests {

	@Autowired
	private UserRepository userRepository;

	@BeforeClass
	public static void beforeClass() {
		DynamoDbTestUtil.configureDynamoDB();
	}

	@AfterClass
	public static void afterClass() {
		DynamoDbTestUtil.deleteTableUser();

	}

	@Test
	public void saveTest() {

		final User user = new User();
		user.setEmail("savetest@mobicare.com.br");
		user.setName("Save User");
		user.setDate(new Date());

		userRepository.save(user);
	}

	@Test
	public void deleteTest() {

		final User user = new User();
		user.setEmail("savetest@mobicare.com.br");
		userRepository.delete(user);
	}

	@Test
	public void getAllTest() {
		final List<User> all = userRepository.getAll();

		Assert.assertFalse(all.isEmpty());
	}

	@Test
	public void getByEmailTest() {

		final User byEmail = userRepository.getByEmail("teste123@mobicare.com.br");

		Assert.assertNotNull(byEmail);

	}

	@Test
	public void searchByNameTest() {

		final List<User> searchByName = userRepository.searchByName("123");

		Assert.assertFalse(searchByName.isEmpty());
	}

}
