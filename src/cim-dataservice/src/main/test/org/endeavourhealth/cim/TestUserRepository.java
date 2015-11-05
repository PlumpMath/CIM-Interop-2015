package org.endeavourhealth.cim;

import org.endeavourhealth.core.repository.user.UserRepository;
import org.endeavourhealth.core.repository.user.User;

public class TestUserRepository extends UserRepository{
	@Override
	public User getByApiKey(String apiKey) {
		User user = new User();
		user.setSecret("password");
		return user;
	}
}
