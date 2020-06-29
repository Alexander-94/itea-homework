package ua.itea.sax;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ua.itea.entity.Dependency;

public class SaxParser extends DefaultHandler {

	private static final String DEPENDENCY = "dependency";
	private static final String GROUP_ID = "groupId";
	private static final String ARTIFACT_ID = "artifactId";
	private static final String VERSION = "version";
	private static final String SCOPE = "scope";
	private List<Dependency> dependencies = new ArrayList<Dependency>();
	private Dependency dependency = new Dependency();

	private boolean isGroupId;
	private boolean isArtifactId;
	private boolean isVersion;
	private boolean isScope;

	public SaxParser() {
		super();
	}

	// document start
	@Override
	public void startDocument() throws SAXException {
		// System.out.println("start document");
	}

	// element opened
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (Objects.equals(qName, DEPENDENCY)) {
			dependency = new Dependency();
		}
		if (Objects.equals(qName, GROUP_ID)) {
			isGroupId = true;
		}
		if (Objects.equals(qName, ARTIFACT_ID)) {
			isArtifactId = true;
		}
		if (Objects.equals(qName, VERSION)) {
			isVersion = true;
		}
		if (Objects.equals(qName, SCOPE)) {
			isScope = true;
		}

	}

	// text info inside elements
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = "";
		for (int i = start; i < start + length; i++) {
			value += ch[i];
		}

		if (isGroupId) {
			dependency.setGroupID(value);
		}
		if (isArtifactId) {
			dependency.setArtifactID(value);
		}
		if (isVersion) {
			dependency.setVersion(value);
		}
		if (isScope) {
			dependency.setScope(value);
		}
	}

	// element closed
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (isGroupId) {
			isGroupId = false;
		}
		if (isArtifactId) {
			isArtifactId = false;
		}
		if (isVersion) {
			isVersion = false;
		}
		if (isScope) {
			isScope = false;
		}

		if (Objects.equals(qName, DEPENDENCY)) {
			dependencies.add(dependency);
			dependency = new Dependency();
		}
	}

	// document end
	@Override
	public void endDocument() throws SAXException {
		// System.out.println("end document");
	}

	public List<Dependency> getDep() {
		return dependencies;
	}

}
