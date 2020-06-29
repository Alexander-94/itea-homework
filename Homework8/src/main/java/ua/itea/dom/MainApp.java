package ua.itea.dom;

import java.util.ArrayList;
import java.util.List;

import ua.itea.entity.Dependency;

public class MainApp {

	public static void main(String[] args) {

		List<Dependency> dep = new ArrayList<Dependency>();
		DomParser domParser = new DomParser(dep);
		domParser.parse();
		
		for (Dependency d: dep) {
			System.out.println(d);
		}
	}
}
