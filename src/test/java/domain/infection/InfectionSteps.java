package domain.infection;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.board.CityName;
import domain.game.TurnId;
import infra.World;
import org.assertj.core.api.Assertions;
import run.AsyncAssertions;
import run.GameHook;

import java.util.concurrent.TimeUnit;

public class InfectionSteps {

    private TurnId currentTurnId;

    @Before
    public void startTurn() {
        currentTurnId = new TurnId();
    }

    @When("^(.*) is infected by (Blue|Black|Red|Yellow)$")
    public void cityIsInfected(CityName cityName, Disease disease) throws Throwable {
        infect(cityName, disease);
    }

    @Then("^(Blue|Black|Red|Yellow) infection level of (.*) should (?:be|stay at) (\\d+)$")
    public void infectionLevelOfParisShouldBe(Disease disease, CityName cityName, int infectionLevel) throws Throwable {
        boolean validated = AsyncAssertions.isTrueWithin(() -> GameHook.RecordEvent.INSTANCE.infectionAppliedEvents.stream()
                .filter(e -> e.disease == disease
                        && e.cityName == cityName
                        && e.infectionLevel.equals(InfectionLevel.from(infectionLevel)))
                .findFirst()
                .isPresent(), 1, TimeUnit.SECONDS);
        Assertions.assertThat(validated).as("infection level should be updated but no infectionAppliedEvent raised").isTrue();
    }

    @And("^(.*) has already been infected by (Blue|Black|Red|Yellow) (\\d+) times$")
    public void cityHasAlreadyBeenInfectedTimes(CityName cityName, Disease disease, int infectionTimes) throws Throwable {
        for (int i = 0; i < infectionTimes; i++) {
            infect(cityName, disease);
            //FIXME Ici on a un problème du fait de l'asynchronisme du bus.
            // Comment faire pour la mise en place de contexte + async ???
            Thread.sleep(10);
        }

    }

    private void infect(CityName cityName, Disease disease) {
        World.eventBus.publish(new InfectionEvent(disease, cityName, currentTurnId));
    }

}
