package com.example.demo;


public class Member {
	
	private String name;
	private int Age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	@Override
	public String toString() {
		return "Member [name=" + name + ", Age=" + Age + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
