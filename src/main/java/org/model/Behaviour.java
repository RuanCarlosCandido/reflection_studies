package org.model;

@CustomClassAnnotation
public interface Behaviour {
	
	@CustomFieldAnnotation
	long SERIAL = 1l;

	@CustomMethodAnnotation
	default String eat() {
		return this + " just eat";
	}

}
