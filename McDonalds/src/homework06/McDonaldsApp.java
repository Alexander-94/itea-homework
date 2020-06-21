package homework06;

public class McDonaldsApp {

	public static void main(String[] args) {

		Object obj = new Object();
		Cashier cashier = new Cashier(obj);
		String[] customers = { "Pudge", "Ancient Apparition", "Bristleback", "Crystal Maiden", "Abadon" };

		for(String s : customers) {
			new Customer(s, cashier);
		}
	}
}
