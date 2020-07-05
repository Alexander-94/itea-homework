package ua.itea;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bus implements Runnable {
	private static final int MAN_GET_ON_TIME = 200;
	private String name;
	private int maxPassengers;
	private int curPassengers;
	private List<City> route;
	private CountDownLatch startLatch;
	private int speed;
	private TextArea tA;
	private Label l;
	private TranslateTransition tS;
	private Rectangle r;
	private Thread t;
	private MusicStopThread musicStopThread;

	public Bus() {
		super();
	}

	public Bus(String name, int maxPassengers, int curPassengers, List<City> route, CountDownLatch startLatch,
			TextArea t1, Label l, TranslateTransition ts, Rectangle r, MusicStopThread musicStopThread) {
		super();
		this.name = name;
		this.maxPassengers = maxPassengers;
		this.curPassengers = curPassengers;
		this.route = route;
		this.startLatch = startLatch;
		this.tA = t1;
		this.l = l;
		this.tS = ts;
		this.r = r;
		this.musicStopThread = musicStopThread;
		Random random = new Random();
		speed = random.nextInt(7);
		t = new Thread(this);
		t.start();
	}

	private boolean isFull;

	@Override
	public void run() {
		while (route.size() > 0) {

			if (startLatch.getCount() > 0) {
				try {
					Platform.runLater(() -> tA.appendText("\nBus:" + name + " is waiting for people"));
					startLatch.await();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (startLatch.getCount() == 0 && isFull == false) {
				synchronized (t) {
					try {
						t.wait();// ждем запуска от последнего пассажира
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Bus" + name + "have waited");
				}
				isFull = true;
			}

			Platform.runLater(() -> tA.appendText("\n" + name + " is driving to: " + route.get(0).getName()));

			int rideTime = route.get(0).getRideTime() - speed;
			if (rideTime <= 0) {
				rideTime = 1;
			}

			moveBus(100, rideTime, tS, r);
			synchronized (t) {
				try {
					t.wait();// ждем окончания анимации движения
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				TimeUnit.SECONDS.sleep(rideTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Platform.runLater(() -> tA.appendText("\n" + name + " is stopping in: " + route.get(0).getName()));
			try {
				TimeUnit.SECONDS.sleep(route.get(0).getStopTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			route.remove(0);
			if (route.size() == 0) {
				Platform.runLater(() -> tA.appendText("\n" + name + " has arrived!"));
				musicStopThread.getMusicStopLatch().countDown();
			}
		}

	}

	private void moveBus(int deltaX, int timeSec, TranslateTransition movement, Rectangle bus) {
		movement = new TranslateTransition(Duration.seconds(timeSec), bus);
		movement.setByX(100);
		movement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				synchronized (t) {
					t.notify();
				}
			}
		});
		movement.play();
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
		synchronized (t) {
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
		return tA;
	}

	public Thread getT() {
		return t;
	}

}
