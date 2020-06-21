package homework06;

public class Customer implements Runnable {

	private String name;
	private Thread t;
	private Cashier ca;

	public Customer(String name, Cashier ca) {
		this.name = name;
		this.ca = ca;
		t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			ca.serve(name);
		}
	}
}
