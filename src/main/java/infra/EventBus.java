package infra;

import domain.cube.NoAvailableCubeLeftEvent;
import domain.cube.NoAvailableCubeLeftListener;
import domain.infection.InfectionAppliedEvent;
import domain.infection.InfectionAppliedListener;
import domain.infection.InfectionEvent;
import domain.infection.InfectionListener;
import domain.infection.outbreak.OutbreakEvent;
import domain.infection.outbreak.OutbreakListener;

import java.util.List;

/**
 * Created by yann on 03/03/18.
 */
public interface EventBus {
    void listenOutbreak(OutbreakListener listener);

    void listenInfection(InfectionListener listener);

    void listenNoAvailableCubeLeft(NoAvailableCubeLeftListener listener);

    void listenInfectionApplied(InfectionAppliedListener listener);

    void publish(InfectionEvent infectionEvent);

    void publish(OutbreakEvent outbreakEvent);

    void publish(NoAvailableCubeLeftEvent noAvailableCubeLeftEvent);

    void publish(InfectionAppliedEvent infectionAppliedEvent);

    @Deprecated
        //Replace this method by OutbreakAppliedEvent + Listener
    List<OutbreakEvent> getOutbreakEvents();
}
