package domain.infection.outbreak;

import cucumber.api.java.en.Then;
import infra.World;
import org.assertj.core.api.Assertions;
import run.AsyncAssertions;

import java.util.concurrent.TimeUnit;

public class OutbreakSteps {

    @Then("^outbreak counter value should be (\\d+)$")
    public void outbreakCounterValueShouldBe(int expectedOutbreakCounter) throws Throwable {
        Assertions.assertThat(AsyncAssertions.isTrueWithin(() -> World.outbreakCounter.value == expectedOutbreakCounter, 3, TimeUnit.SECONDS))
                .as("outbreak counter should be " + expectedOutbreakCounter + "but was " + World.outbreakCounter.value).isTrue();
    }
}
