package domain.infection;


import domain.board.CityName;

public class InfectionAppliedEvent {

    public final Disease disease;
    public final CityName cityName;
    public final InfectionLevel infectionLevel;

    public InfectionAppliedEvent(Disease disease, CityName cityName, InfectionLevel infectionLevel) {
        this.disease = disease;
        this.cityName = cityName;
        this.infectionLevel = infectionLevel;
    }
}
