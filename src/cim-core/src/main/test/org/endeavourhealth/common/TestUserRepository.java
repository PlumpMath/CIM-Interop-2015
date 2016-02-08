package org.endeavourhealth.common;

import org.endeavourhealth.core.repository.user.UserRepository;
import org.endeavourhealth.core.repository.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUserRepository extends UserRepository{
	private Map<String, User> _users;

	public TestUserRepository() {
		_users = new HashMap<>();
	}

	public void addTestUser(String apikey, String secret) {
		User user = _users.get(apikey);

		if (user == null)
			user = new User();

		user.setApiKey(apikey);
		user.setSecret(secret);
		_users.put(apikey, user);
	}

	@Override
	public User getByApiKey(String apiKey) {
		return _users.get(apiKey);
	}
}
