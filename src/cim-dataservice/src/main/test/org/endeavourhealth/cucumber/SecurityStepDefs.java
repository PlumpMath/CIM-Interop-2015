package org.endeavourhealth.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.endeavourhealth.common.core.ExchangeHelper;
import org.endeavourhealth.common.core.HeaderKey;
import org.endeavourhealth.common.processor.SecurityProcessor;

public class SecurityStepDefs {
	private Exchange _exchange;
	private Exception _exceptionThrown;

	@Given("^An inbound exchange message$")
	public void anInboundExchangeMessage() throws Throwable {
		_exchange = new DefaultExchange(new DefaultCamelContext());
		_exceptionThrown = null;
	}

	@And("^A an apiKey of (.*)$")
	public void aAnApiKeyOf(String arg0) throws Throwable {
		ExchangeHelper.setInHeaderString(_exchange, HeaderKey.ApiKey, arg0);
	}

	@And("^An inbound hash of (.*)$")
	public void anInboundHashOf(String arg0) throws Throwable {
		ExchangeHelper.setInHeaderString(_exchange, HeaderKey.Hash, arg0);
	}

	@And("^A method of (.*)$")
	public void aMethodOf(String arg0) throws Throwable {
		_exchange.getIn().setHeader("CamelHttpPath", arg0);
	}

	@And("^A body of (.*)$")
	public void aBodyOf(String arg0) throws Throwable {
		_exchange.getIn().setBody(arg0);
	}

	@When("^The message is validated$")
	public void theMessageIsValidated() throws Throwable {
		try {
			new SecurityProcessor().process(_exchange);
			_exceptionThrown = null;
		}
		catch (Exception e)
		{
			_exceptionThrown = e;
		}
	}

	@Then("^The following exception will be thrown (.*)$")
	public void theFollowingExceptionWillBeThrownException(String arg0) throws Throwable {
		if (arg0.toLowerCase().equals("#null#"))
			Assert.assertNull(_exceptionThrown);
		else
			Assert.assertEquals(arg0, _exceptionThrown.getClass().getSimpleName());
	}

	@And("^The exception message will be (.*)$")
	public void theExceptionMessageWillBeMessage(String arg0) throws Throwable {
		if ("".equals(arg0))
			arg0 = null;

		if (_exceptionThrown != null)
			Assert.assertEquals(arg0, _exceptionThrown.getMessage());
	}}
