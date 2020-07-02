package ua.itea;

import java.util.Random;

import javafx.application.Platform;

public class Passenger implements Runnable {

	private String name;
	private boolean isInBus = false;
	private Bus b1;
	private Bus b2;
	private Bus b3;
	private String message;
	private boolean isInMask;
	private Random random;
	private boolean isBanned;

	public Passenger() {
		super();
	}

	public Passenger(String name, Bus b1, Bus b2, Bus b3) {
		super();
		this.name = name;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		random = new Random();
		this.isInMask = random.nextBoolean();
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		while (!isInBus) {

			// passenger has been banned for not wearing a mask
			if (isBanned) {
				return;
			}

			int c = random.nextInt(3);
			if (c == 0 && b1.getStartLatch().getCount() > 0) {
				if (isInMask) {
					b1.getStartLatch().countDown();
					b1.getIntoTheBus();
					//System.out.println("Passenger: " + name + " got into the bus!");
					message = "\nPassenger: " + name + " got into the bus!" + b1.getName();
					Platform.runLater(() -> b1.getT1().appendText(message));
					isInBus = true;
				} else {
					//System.out.println("\nPassenger: " + name + " no mask! Go to hell!");
					message = "\nPassenger: " + name + " no mask! Go to hell!" + b1.getName();
					Platform.runLater(() -> b1.getT1().appendText(message));
					this.isInMask = random.nextBoolean();
					if (!isInMask) {
						isBanned = true;
						message = "\nPassenger: " + name + " has been banned for not wearing a mask!";
						Platform.runLater(() -> b1.getT1().appendText(message));
					}
				}
			} else if (c == 1 && b2.getStartLatch().getCount() > 0) {
				if (isInMask) {
					b2.getStartLatch().countDown();
					b2.getIntoTheBus();
					//System.out.println("Passenger: " + name + " got into the bus!");
					message = "\nPassenger: " + name + " got into the bus!" + b2.getName();
					Platform.runLater(() -> b2.getT1().appendText(message));
					isInBus = true;
				} else {
					//System.out.println("\nPassenger: " + name + " no mask! Go to hell!");
					message = "\nPassenger: " + name + " no mask! Go to hell!" + b2.getName();
					Platform.runLater(() -> b2.getT1().appendText(message));
					this.isInMask = random.nextBoolean();
					if (!isInMask) {
						isBanned = true;
						message = "\nPassenger: " + name + " has been banned for not wearing a mask!";
						Platform.runLater(() -> b2.getT1().appendText(message));
					}
				}
			} else if (c == 2 && b3.getStartLatch().getCount() > 0) {
				if (isInMask) {
					b3.getStartLatch().countDown();
					b3.getIntoTheBus();
					//System.out.println("Passenger: " + name + " got into the bus!");
					message = "\nPassenger: " + name + " got into the bus!" + b3.getName();
					Platform.runLater(() -> b3.getT1().appendText(message));
					isInBus = true;
				} else {
					//System.out.println("\nPassenger: " + name + " no mask! Go to hell!");
					message = "\nPassenger: " + name + " no mask! Go to hell!" + b3.getName();
					Platform.runLater(() -> b3.getT1().appendText(message));
					this.isInMask = random.nextBoolean();
					if (!isInMask) {
						isBanned = true;
						message = "\nPassenger: " + name + " has been banned for not wearing a mask!";
						Platform.runLater(() -> b3.getT1().appendText(message));
					}
				}
			}
			/*
			 * if (b1.getStartLatch().getCount() > 0) {
			 * 
			 * } else if (b2.getStartLatch().getCount() > 0) {
			 * 
			 * 
			 * } else if (b3.getStartLatch().getCount() > 0) {
			 * 
			 * }
			 */
		}
	}

}
