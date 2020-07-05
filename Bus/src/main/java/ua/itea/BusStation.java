package ua.itea;

import java.util.List;
import java.util.Random;

public class BusStation {

	private List<City> route;
	private DBWorker dbWorker;
	private Random random;

	public BusStation() {
		super();
		this.random = new Random();
	}

	public BusStation(DBWorker dbWorker) {
		super();
		this.random = new Random();
		this.dbWorker = dbWorker;
	}

	public List<City> getRoute() {
		route = dbWorker.getAllCities();
		return route;
	}

}
