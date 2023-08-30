package edu.Roma42.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Classes:");
		System.out.println("User");
		System.out.println("Car");
		System.out.println("---------------------");

		System.out.println("Enter class name:");
		String className = scanner.nextLine();

		Class<?> clazz = Class.forName("edu.Roma42.classes." + className);
		System.out.println("---------------------");
		System.out.println("fields:");
		for (Field field : clazz.getDeclaredFields()) {
			System.out.println(field.getType().getSimpleName() + " " + field.getName());
		}

		System.out.println("methods:");
		for (Method method : clazz.getDeclaredMethods()) {
			System.out.print(method.getReturnType().getSimpleName() + " " + method.getName() + "(");
			for (int i = 0; i < method.getParameterTypes().length; i++) {
				System.out.print(method.getParameterTypes()[i].getSimpleName());
				if (i != method.getParameterTypes().length - 1)
					System.out.print(",");
			}
			System.out.println(")");
		}
		System.out.println("---------------------");

		Constructor<?> emptyConstructor = clazz.getDeclaredConstructor();
		Object object = emptyConstructor.newInstance();

		System.out.println("Let's create an object.");
		for (Field field : clazz.getDeclaredFields()) {
			System.out.print(field.getName() + ": ");
			String value = scanner.nextLine();
			setField(object, field, value);
		}

		System.out.println("Object created: " + object);
		System.out.println("---------------------");

		System.out.println("Enter name of the field for changing:");
		String fieldName = scanner.nextLine();

		Field fieldToUpdate = clazz.getDeclaredField(fieldName);
		System.out.println("Enter " + fieldToUpdate.getType().getSimpleName() + " value:");
		String valueToUpdate = scanner.nextLine();
		setField(object, fieldToUpdate, valueToUpdate);
		System.out.println("Object updated: " + object);
		System.out.println("---------------------");

		System.out.println("Enter name of the method for call:");
		String methodName = scanner.nextLine();
		char par = '(';
		String trimmethodName = methodName.substring(0, methodName.indexOf(par));
		Class<?>[] parameterTypes = null;
		int count = 0;

		for (char c : methodName.toCharArray()) {
			if (c == ',')
				count++;
		}
		for (Method meth : clazz.getDeclaredMethods()) {
			if (meth.getName().equals(trimmethodName)) {
				parameterTypes = meth.getParameterTypes();
				if (count != meth.getParameterTypes().length - 1) {
					System.err.println("method declaration wrong");
					System.exit(1);
				}
			}
		}

		LinkedList<Object> Values = new LinkedList<>();

		Method methodToCall = clazz.getDeclaredMethod(trimmethodName, parameterTypes);
		for (int i = 0; i < methodToCall.getParameterTypes().length; i++) {
			System.out.println("Enter " + methodToCall.getParameterTypes()[i].getSimpleName() + " value:");
			if (methodToCall.getParameterTypes()[i].getSimpleName().equals("int")) {
				int intValue = scanner.nextInt();
				Values.add(intValue);
			}
			else {
				String Value = scanner.nextLine();
				Values.add(Value);
			}
		}

		Object result = methodToCall.invoke(object, Values.toArray());

		if (methodToCall.getReturnType() != void.class) {
			System.out.println("Method returned:");
			System.out.println(result);
		}
		scanner.close();
	}

	private static void setField(Object object, Field field, String value) throws Exception {
		field.setAccessible(true);
		Class<?> fieldType = field.getType();
		if (fieldType == String.class) {
			field.set(object, value);
		} else if (fieldType == int.class) {
			field.set(object, Integer.parseInt(value));
		} else if (fieldType == double.class) {
			field.set(object, Double.parseDouble(value));
		} else if (fieldType == boolean.class) {
			field.set(object, Boolean.parseBoolean(value));
		} else if (fieldType == long.class) {
			field.set(object, Long.parseLong(value));
		}
		field.setAccessible(false);
	}
}
