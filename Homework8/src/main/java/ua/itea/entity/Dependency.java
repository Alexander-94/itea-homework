package ua.itea.entity;

public class Dependency {

	private String groupID;
	private String artifactID;
	private String version;
	private String scope;

	public Dependency() {
		super();
	}

	public Dependency(String groupID, String artifactID, String version, String scope) {
		super();
		this.groupID = groupID;
		this.artifactID = artifactID;
		this.version = version;
		this.scope = scope;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getArtifactID() {
		return artifactID;
	}

	public void setArtifactID(String artifactID) {
		this.artifactID = artifactID;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Dependency [groupID=" + groupID + ", artifactID=" + artifactID + ", version=" + version + ", scope="
				+ scope + "]";
	}

}
