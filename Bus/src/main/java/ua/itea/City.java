package ua.itea;

public class City {

	private int id;
	private String name;
	private String description;
	private int rideTime;
	private int stopTime;

	public City() {
		super();
	}

	public City(int id, String name, String description, int rideTime, int stopTime) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.rideTime = rideTime;
		this.stopTime = stopTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRideTime() {
		return rideTime;
	}

	public void setRideTime(int rideTime) {
		this.rideTime = rideTime;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", description=" + description + ", rideTime=" + rideTime
				+ ", stopTime=" + stopTime + "]";
	}

}
