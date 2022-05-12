package org.hugevictory.hugevictory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String UUID;
	private String name;
	private int MAX_LENGTH_UUID = 5;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUUID() {
		return UUID;
	}
	
	public void setUUID(String UUID) {
		try{
			if(UUID.length() != MAX_LENGTH_UUID){
				throw new StringTooLongException();
			}
			this.UUID = UUID;
		}
		catch (StringTooLongException e){
			System.out.println("UUID != 5");
		}
	}

	public String generateRandomUUID(){
		String randomUUID = "";
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedCharacters = capitalCaseLetters + lowerCaseLetters +specialCharacters + numbers;
		for (int i = 0; i < MAX_LENGTH_UUID; i++){
			randomUUID += combinedCharacters.charAt((int) (Math.random() *combinedCharacters.length()));
		}
		return randomUUID;
	}

	public void setRandomUUID(){
		this.UUID = generateRandomUUID();
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
}
