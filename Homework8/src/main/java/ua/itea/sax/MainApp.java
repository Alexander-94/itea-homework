package ua.itea.sax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import ua.itea.entity.Dependency;

public class MainApp {

	private static final String FILE_PATH = "pom.xml";

	public static void main(String[] args) {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}

		SaxParser saxParser = new SaxParser();
		try {
			parser.parse(new File(FILE_PATH), saxParser);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		List<Dependency> dependencies = new ArrayList<Dependency>();
		dependencies = saxParser.getDep();

		for (Dependency d : dependencies) {
			System.out.println(d);
		}
	}
}
