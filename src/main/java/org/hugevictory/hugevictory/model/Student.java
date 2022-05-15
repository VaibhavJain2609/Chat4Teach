package org.hugevictory.hugevictory.model;

import javax.persistence.*;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(unique = true)
	private String UUID;
	private String name;
	private boolean chatEnabled;

	public boolean isChatEnabled() {
		return chatEnabled;
	}
	public void setIsChatEnabled(boolean chatEnabled) {
		this.chatEnabled = !chatEnabled;
	}

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
		String numbers = "1234567890";
		String combinedCharacters = capitalCaseLetters + lowerCaseLetters + numbers;
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
