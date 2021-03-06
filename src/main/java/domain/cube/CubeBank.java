package domain.cube;


import domain.infection.Disease;
import infra.World;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CubeBank {

    private static final int CUBE_INITIAL_NUMBER = 24;

    private Map<Disease, Integer> cubesByDisease;

    public CubeBank() {
        cubesByDisease = new HashMap<>();
        Stream.of(Disease.values())
                .forEach(this::initCubeNumber);
    }

    private void initCubeNumber(Disease disease) {
        cubesByDisease.put(disease, CUBE_INITIAL_NUMBER);
    }

    public Integer getRemainingCubes(Disease disease) {
        return cubesByDisease.get(disease);
    }

    public void takeCube(Disease disease) {
        cubesByDisease.put(disease, getRemainingCubes(disease) - 1);

        if (getRemainingCubes(disease) == 0) {
            World.eventBus.publish(new NoAvailableCubeLeftEvent(disease));
        }
    }

    public void setCubeNumber(Disease disease, int cubesleft) {
        cubesByDisease.put(disease, cubesleft);
    }
}
