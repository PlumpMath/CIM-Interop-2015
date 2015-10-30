package org.endeavourhealth.cim;

import org.endeavourhealth.core.repository.user.data.UserRepository;
import org.endeavourhealth.core.repository.user.model.User;

public class TestUserRepository extends UserRepository{
	@Override
	public User getByApiKey(String apiKey) {
		User user = new User();
		user.setSecret("password");
		return user;
	}
}
