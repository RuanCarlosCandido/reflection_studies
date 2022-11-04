package org.model;

@Deprecated
@CustomClassAnnotation
public class Cat extends Animal{

	String age;
	
	public String meow() {
		return "meow";
	}
}
