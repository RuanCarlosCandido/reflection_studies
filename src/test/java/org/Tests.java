package org;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.constant.ClassDesc;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.model.Animal;
import org.model.Cat;
import org.model.CustomClassAnnotation;
import org.model.Dog;
import org.model.Square;

@SuppressWarnings("unused")
public class Tests {

	@Test
	public void mother_cannot_be_cast_to_son() {

		assertThrows(ClassCastException.class, () -> {
			Class<Animal> superClass = Animal.class;

			Class<? extends Dog> subClass = superClass.asSubclass(Dog.class);
		});
	}

	@Test
	public void son_can_be_cast_to_Mother() {
		
		Class<Dog> superClass = Dog.class;
		Class<? extends Animal> subClass = superClass.asSubclass(Animal.class);
	}

	@Test
	public void cannot_cast_to_a_class_that_is_not_extended() {

		assertThrows(ClassCastException.class, () -> {
			Class<Square> superClass = Square.class;
			Class<? extends Animal> subClass = superClass.asSubclass(Animal.class);
		});
	}

	@Test
	public void casting_son_to_mother_should_sucess() {

		Class<Animal> clazz = Animal.class;
		Animal casted = clazz.cast(new Dog());
	}

	@Test
	public void get_class_representing_the_type_of_array_should_sucess() {

		Class<? extends Dog[]> clazz = new Dog[1].getClass();
		Class<?> componentType = clazz.componentType();

		assertEquals(Dog.class, componentType);
	}

	@Test
	public void get_package_descriptor_should_sucess() {

		Class<Dog> clazz = Dog.class;
		assertEquals("Lorg/model/Dog;", clazz.descriptorString());

	}

	@Test
	public void class_desc_exploration_should_sucess() throws ReflectiveOperationException {

		Class<Dog> clazz = Dog.class;
		Optional<ClassDesc> classDescOpt = clazz.describeConstable();
		ClassDesc classDesc = classDescOpt.get();

		assertEquals("Dog[]", classDesc.arrayType().displayName());
		assertEquals("Dog[][]", classDesc.arrayType(2).displayName());
		assertEquals("Dog", classDesc.displayName());
		assertEquals(true, classDesc.isClassOrInterface());
		assertEquals(false, classDesc.isPrimitive());
		assertEquals("org.model", classDesc.packageName());
		assertEquals("class org.model.Dog", classDesc.resolveConstantDesc(MethodHandles.lookup()).toString()); // object

	}

	// TODO understand better
	@Test
	public void annotated_types_exploration_should_sucess() {

		Class<Dog> clazz = Dog.class;
		AnnotatedType[] annotatedTypes = clazz.getAnnotatedInterfaces();

		for (AnnotatedType annotatedType : annotatedTypes) {
			assertEquals(null, annotatedType.getAnnotatedOwnerType());

			assertEquals(new ArrayList<Object>(), Arrays.asList(annotatedType.getAnnotations()));

			assertEquals(new ArrayList<Object>(), Arrays.asList(annotatedType.getDeclaredAnnotations()));

			assertEquals("org.model.Behaviour", annotatedType.getType().getTypeName());

		}

	}

	// TODO understand better
	@Test
	public <T> void annotated_superclass_exploration_should_sucess() {

		Class<Dog> clazz = Dog.class;
		AnnotatedType annotatedTypes = clazz.getAnnotatedSuperclass();

		assertEquals(null, annotatedTypes.getAnnotatedOwnerType());
		assertEquals(0, annotatedTypes.getAnnotations().length);
		assertEquals(0, annotatedTypes.getDeclaredAnnotations().length);
		assertEquals("org.model.Animal", annotatedTypes.getType().getTypeName());
		assertEquals(false, annotatedTypes.isAnnotationPresent(CustomClassAnnotation.class)); // ?

	}

	@Test
	public void getting_custom_annotations_does_not_work() {

		Class<Cat> clazz = Cat.class;

		List<String> annotations = Arrays.asList(clazz.getAnnotations()).stream().map(annotation -> annotation.annotationType().getSimpleName()).toList();

		assertTrue(annotations.contains("Deprecated"));
		assertFalse(annotations.contains("CustomClassAnnotation"));
		
	}
	
	@Test
	public void class_loader_exploration_should_sucess() {

		Class<Dog> clazz = Dog.class;

		ClassLoader classLoader = clazz.getClassLoader();
		
		assertEquals("AppClassLoader", classLoader.getClass().getSimpleName());
		assertEquals("app", classLoader.getName());
//		assertEquals("", Arrays.asList(classLoader.getDefinedPackages()));
		assertEquals("platform", classLoader.getParent().getName());
//		assertEquals("", classLoader.getUnnamedModule());
		assertEquals("platform", ClassLoader.getPlatformClassLoader().getName());
		assertEquals("app", ClassLoader.getSystemClassLoader().getName());

		
	}
	
	@Test
	public void constructors_exploration_should_sucess() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<Dog> clazz = Dog.class;

		Constructor<Dog> constructorUsingName = clazz.getConstructor(String.class);
		Dog instanceViaReflection = constructorUsingName.newInstance(new Object[] {"Diana"});
		
		assertEquals("Diana", instanceViaReflection.toString());
		assertEquals(1, constructorUsingName.getParameterCount());
				
	}
}
