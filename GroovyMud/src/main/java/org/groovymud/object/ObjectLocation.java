package org.groovymud.object;

public class ObjectLocation {

	String definition = "";
	String beanId = "";
	String areaName = ""; // incase we need a 3rd point of reference

	public ObjectLocation() {
	}

	public ObjectLocation(ObjectLocation objectLocation) {
		if(objectLocation == null){
			throw new IllegalArgumentException("objectLocation cannot be null");
		}
		this.definition = objectLocation.definition;
		this.beanId = objectLocation.beanId;
		this.areaName = objectLocation.areaName;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definitionScriptLocation) {
		this.definition = definitionScriptLocation;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String locationBeanName) {
		this.beanId = locationBeanName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return areaName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ObjectLocation)) {
			return false;
		}
		ObjectLocation other = (ObjectLocation) obj;
		return definition == other.definition && beanId == other.beanId && this.areaName == other.areaName;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + definition.hashCode() + beanId.hashCode() + areaName.hashCode();
	}
}
