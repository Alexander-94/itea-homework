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

	public BusStation(List<City> route) {
		super();
		this.route = route;
		this.random = new Random();
	}

	public synchronized List<City> getRoute() {
		dbWorker = new DBWorker();
		route = dbWorker.getAllCities();
		//System.out.println("a" + route.toString());
		return route;
	}

}
