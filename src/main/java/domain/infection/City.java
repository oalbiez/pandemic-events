package domain.infection;

import domain.board.CityName;

public class City {

	private final CityName cityName;
	private InfectionLevel blueLevel;

	public City(CityName cityName) {
		this.cityName = cityName;
		this.blueLevel = InfectionLevel.from(0);
	}

	public CityName name() {
		return cityName;
	}

	public void infect(Disease disease){
		if (Disease.BLUE.equals(disease)) {
			blueLevel = blueLevel.increase();
		}
	}

	public InfectionLevel infectionLevel() {
		return blueLevel;
	}

	public boolean outbreakInfectionLevelReached() {
		return blueLevel.outbreakLevelReached();
	}


}