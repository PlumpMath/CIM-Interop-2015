package org.endeavourhealth.cucumber;

import cucumber.api.java.en.And;
import org.endeavourhealth.common.TestUserRepository;
import org.endeavourhealth.core.repository.user.UserRepository;

public class BackgroundStepDefs {
	TestUserRepository _testUserRepository;

	@And("^A repository of Users$")
	public void aRepositoryofUsers() throws Throwable {
		_testUserRepository = new TestUserRepository();
		UserRepository.setInstance(_testUserRepository);
	}

	@And("^A user with an api key (.*) and a secret (.*)$")
	public void aUserWithAnApiKeyAndASecret(String apiKey, String secret) throws Throwable {
		if (secret.toLowerCase().equals("#null#"))
			secret = null;

		_testUserRepository.addTestUser(apiKey, secret);
	}
}
