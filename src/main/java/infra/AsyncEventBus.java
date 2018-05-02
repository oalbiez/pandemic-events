package infra;


import domain.cube.NoAvailableCubeLeftEvent;
import domain.cube.NoAvailableCubeLeftListener;
import domain.infection.InfectionAppliedEvent;
import domain.infection.InfectionAppliedListener;
import domain.infection.InfectionEvent;
import domain.infection.InfectionListener;
import domain.infection.outbreak.OutbreakEvent;
import domain.infection.outbreak.OutbreakListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncEventBus implements EventBus {

    private final List<OutbreakEvent> outbreakEvents = new ArrayList<>();
    private final List<InfectionEvent> infectionEvents = new ArrayList<>();
    private final List<InfectionListener> infectionListeners = new ArrayList<>();
    private final List<OutbreakListener> outbreakListeners = new ArrayList<>();
    private final List<NoAvailableCubeLeftListener> noAvailableCubeLeftListeners = new ArrayList<>();
    private final List<InfectionAppliedListener> infectionAppliedEventListeners = new ArrayList<>();
    private NoAvailableCubeLeftEvent noAvailableCubeLeftEvent;

    @Override
    public void listenOutbreak(OutbreakListener listener) {
        outbreakListeners.add(listener);
    }

    @Override
    public void listenInfection(InfectionListener listener) {
        infectionListeners.add(listener);
    }

    @Override
    public void listenNoAvailableCubeLeft(NoAvailableCubeLeftListener listener) {
        noAvailableCubeLeftListeners.add(listener);
    }

    @Override
    public void listenInfectionApplied(InfectionAppliedListener listener) {
        infectionAppliedEventListeners.add(listener);
    }

    @Override
    public void publish(InfectionEvent infectionEvent) {
        infectionEvents.add(infectionEvent);
        CompletableFuture.runAsync(() -> infectionListeners.forEach(l -> l.onInfection(infectionEvent)));
    }

    @Override
    public void publish(OutbreakEvent outbreakEvent) {
        outbreakEvents.add(outbreakEvent);
        CompletableFuture.runAsync(() -> outbreakListeners.forEach(l -> l.onOutbreak(outbreakEvent)));
    }

    @Override
    public void publish(NoAvailableCubeLeftEvent noAvailableCubeLeftEvent) {
        this.noAvailableCubeLeftEvent = noAvailableCubeLeftEvent;
        CompletableFuture.runAsync(() -> noAvailableCubeLeftListeners.forEach(l -> l.onNoAvailableCubeLeft(noAvailableCubeLeftEvent)));
    }

    @Override
    public void publish(InfectionAppliedEvent infectionAppliedEvent) {
        CompletableFuture.runAsync(() -> infectionAppliedEventListeners.forEach(listener -> listener.onInfectionApplied(infectionAppliedEvent)));
    }

    @Override
    public List<OutbreakEvent> getOutbreakEvents() {
        return outbreakEvents;
    }
}
