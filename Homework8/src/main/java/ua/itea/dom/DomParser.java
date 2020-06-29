package ua.itea.dom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.itea.entity.Dependency;

public class DomParser {

	private static final String FILE_PATH = "pom.xml";
	private static final String MAIN_NODE = "dependencies";
	private static final String GROUP_ID = "groupId";
	private static final String ARTIFACT_ID = "artifactId";
	private static final String VERSION = "version";
	private static final String SCOPE = "scope";

	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private List<Dependency> dep;

	public DomParser() {
		super();
		factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public DomParser(List<Dependency> dep) {
		this();
		this.dep = dep;
	}

	public void parse() {

		try {
			Document doc = builder.parse(new File(FILE_PATH));
			NodeList ns = doc.getElementsByTagName(MAIN_NODE);
			Node n = ns.item(0);
			NodeList nsChilds = null;

			try {
				nsChilds = n.getChildNodes();
			} catch (NullPointerException e) {
				System.out.println("Main node[" + MAIN_NODE + "]doesn't exist");
				System.exit(0);
			}

			for (int j = 0; j < nsChilds.getLength(); j++) {
				Node node = nsChilds.item(j);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Dependency d = new Dependency();
					NodeList id = ((Element) node).getElementsByTagName(GROUP_ID);
					NodeList art = ((Element) node).getElementsByTagName(ARTIFACT_ID);
					NodeList vers = ((Element) node).getElementsByTagName(VERSION);
					NodeList scp = ((Element) node).getElementsByTagName(SCOPE);

					if (id.getLength() != 0) {
						d.setGroupID(((Element) id.item(0)).getTextContent());
					}

					if (art.getLength() != 0) {
						d.setArtifactID(((Element) art.item(0)).getTextContent());
					}

					if (vers.getLength() != 0) {
						d.setVersion(((Element) vers.item(0)).getTextContent());
					}

					if (scp.getLength() != 0) {
						d.setScope(((Element) scp.item(0)).getTextContent());
					}

					dep.add(d);
				}
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
