package run;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import domain.infection.InfectionAppliedEvent;
import domain.infection.InfectionAppliedListener;
import infra.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameHook {

    @After
    public void reset() {
        RecordEvent.INSTANCE.reset();
    }

	@Before
	public void create() {
		World.create();
        World.eventBus.listenInfectionApplied(RecordEvent.INSTANCE);
    }

    public enum RecordEvent implements InfectionAppliedListener {
        INSTANCE;

        public final List<InfectionAppliedEvent> infectionAppliedEvents = new CopyOnWriteArrayList<>();

        public void reset() {
            infectionAppliedEvents.clear();
        }

        @Override
        public void onInfectionApplied(InfectionAppliedEvent infectionAppliedEvent) {
            infectionAppliedEvents.add(infectionAppliedEvent);
        }
    }
}
