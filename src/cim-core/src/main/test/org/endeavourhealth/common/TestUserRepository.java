package org.endeavourhealth.repository;

import org.endeavourhealth.cim.repository.domains.user.UserRepository;
import org.endeavourhealth.cim.repository.domains.user.User;

import java.util.HashMap;
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
