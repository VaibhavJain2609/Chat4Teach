package org.hugevictory.hugevictory.model;

public class Student {
	private int UUID;
	private String name;
	
	public Student(int UUID, String name) {
		this.UUID = UUID;
		this.name = name;
	}
	
	public int getUUID() {
		return UUID;
	}
	
	public void setUUID(int uUID) {
		UUID = uUID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
