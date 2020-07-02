package ua.itea;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Bus implements Runnable {

	private static final int MAN_GET_ON_TIME = 200;
	private String name;
	private int maxPassengers;
	private int curPassengers;
	private List<City> route;
	private CountDownLatch startLatch;
	private int speed;
	private TextArea t1;
	private Label l;

	private Object o;

	public Bus() {
		super();
	}

	public Bus(String name, int maxPassengers, int curPassengers, List<City> route, CountDownLatch startLatch,
			TextArea t1, Label l, Object o) {
		super();
		this.name = name;
		this.maxPassengers = maxPassengers;
		this.curPassengers = curPassengers;
		this.route = route;
		this.startLatch = startLatch;
		this.t1 = t1;
		this.l = l;
		this.o = o;
		Random random = new Random();
		speed = random.nextInt(10);

		Thread t = new Thread(this);
		t.start();
		//System.out.println("Bus:" + name + " starts!");
	}

	@Override
	public void run() {
		while (route.size() > 0) {

			if (startLatch.getCount() > 0) {
				try {
					//System.out.println("Bus:" + name + " is waiting for people");
					Platform.runLater(() -> t1.appendText("\nBus:" + name + " is waiting for people"));
					startLatch.await();

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			try {
				int c = MAN_GET_ON_TIME * maxPassengers;
				TimeUnit.MILLISECONDS.sleep(c);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			//System.out.println("Bus: " + name + " is driving to:" + route.get(0).getName());
			Platform.runLater(() -> t1.appendText("\nBus: " + name + " is driving to:" + route.get(0).getName()));
			int rideTime = route.get(0).getRideTime();
			try {
				int tmp = rideTime - speed;
				TimeUnit.SECONDS.sleep(tmp);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("Bus: " + name + " is stopped in: " + route.get(0).getName());
			Platform.runLater(() -> t1.appendText("\nBus: " + name + " is stopped in:" + route.get(0).getName()));

			int stopTime = route.get(0).getStopTime();
			try {
				TimeUnit.SECONDS.sleep(stopTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			route.remove(0);
			if (route.size() == 0) {
				//System.out.println("Bus: " + name + " has arrived!");
				Platform.runLater(() -> t1.appendText("\nBus: " + name + " has arrived!"));
			}
		}

	}

	public int getMaxPassengers() {
		return maxPassengers;
	}

	public String getName() {
		return name;
	}

	public int getSpeed() {
		return speed;
	}

	public int getCurPassengers() {
		return curPassengers;
	}

	public void getIntoTheBus() {
		synchronized (o) {
			curPassengers += 1;
			try {
				TimeUnit.MILLISECONDS.sleep(MAN_GET_ON_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> l.setText("Occ:" + curPassengers));
		}

	}

	public synchronized CountDownLatch getStartLatch() {
		return startLatch;
	}

	public TextArea getT1() {
		return t1;
	}

}
