package homework06;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cashier implements Runnable {

	private Object obj;
	private Thread t;
	private Random rnd = new Random();
	private static final int SERVE_SECONDS = 5;

	public Cashier(Object obj) {
		this.obj = obj;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		System.out.println("Casier starts Mac");
		while (true) {
		}
	}

	public void serve(String name) {
		synchronized (obj) {
			System.out.println("Free window! ");
			System.out.println("Cashier servs: " + name);
			try {
				TimeUnit.SECONDS.sleep(SERVE_SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int rndVal = rnd.nextInt(2);
			if (rndVal == 1) {
				System.out.println("Cashier closed Mac");
				System.exit(0);
			}
		}
	}

}
