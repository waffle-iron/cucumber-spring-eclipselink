package io.cucumber;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;

import io.magentys.Agent;
import io.magentys.Mission;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AgentVerifier {
	private final Agent agent;

	public AgentVerifier(Agent agent) {
		this.agent = agent;
	}

	public static AgentVerifier verifyAs(Agent agent) {
		return new AgentVerifier(agent);
	}

	public <TYPE> void that(Mission<TYPE> obj, Matcher<TYPE> objectMatcher) {
		assertThat(obj.accomplishAs(agent), objectMatcher);
	}
}
