package ua.itea;

import java.util.Random;
import javafx.application.Platform;

public class Passenger implements Runnable {
	private String name;
	private boolean isInBus;
	private Bus b1;
	private Bus b2;
	private Bus b3;
	private String message;
	private boolean isInMask;
	private Random random;
	private boolean isBanned;
	private int bannedCnt;

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
		this.bannedCnt = 0;
		int r = 03072020;
		int tmp = random.nextInt(r);
		if (tmp > r / 2) {
			this.isInMask = true;
		} else {
			this.isInMask = false;
		}
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		while (!isInBus) {
			int c = random.nextInt(3);
			if (c == 0 && b1.getStartLatch().getCount() > 0 && b1.getCurPassengers() != b1.getMaxPassengers()) {
				passengerGetsInBus(isInMask, b1);
			} else if (c == 1 && b2.getStartLatch().getCount() > 0 && b2.getCurPassengers() != b2.getMaxPassengers()) {
				passengerGetsInBus(isInMask, b2);
			} else if (c == 2 && b3.getStartLatch().getCount() > 0 && b3.getCurPassengers() != b3.getMaxPassengers()) {
				passengerGetsInBus(isInMask, b3);
			}

			// passenger has been banned for not wearing a mask
			if (isBanned) {
				return;
			}
		}
	}

	private void passengerGetsInBus(boolean isInMask, Bus b) {
		if (isInMask) {
			b.getStartLatch().countDown();
			b.getIntoTheBus();
			message = "\nPassenger: " + name + " got into the bus!";
			Platform.runLater(() -> b.getT1().appendText(message));
			isInBus = true;
			if (b.getStartLatch().getCount() == 0) {
				synchronized (b.getT()) {
					b.getT().notify(); // пробуждаем 1 рандомный поток на этом мониторе (спит только Bus)
				}
			}
		} else if (!isInMask) {
			if (!isBanned) {
				message = "\nPassenger: " + name + " no mask! Ride bicycle!";
				Platform.runLater(() -> b.getT1().appendText(message));
				this.isInMask = random.nextBoolean();
				if (!isInMask) {
					bannedCnt++;
				}
				if (bannedCnt >= 2) {
					isBanned = true;
				}
			}
			if (isBanned) {
				message = "\nPassenger: " + name + " has been banned for not wearing a mask!";
				Platform.runLater(() -> b.getT1().appendText(message));
			}
		}
	}

}
