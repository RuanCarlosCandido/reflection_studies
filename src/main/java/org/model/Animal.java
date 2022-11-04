package org.model;

@CustomClassAnnotation
public abstract class Animal {
	@CustomFieldAnnotation
	public String name;

	@Override
	public String toString() {
		return name;
	}

}
