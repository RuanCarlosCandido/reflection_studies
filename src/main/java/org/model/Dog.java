package org.model;

@CustomClassAnnotation
public class Dog extends Animal implements Behaviour {

	@CustomFieldAnnotation
	int age;

	public Dog(){
		
	}
	
	public Dog(String name){
		super.name = name;
	}
	
	private Dog(String name, int age){
		this.age = age;
		super.name = name;
	}
	
	
}
