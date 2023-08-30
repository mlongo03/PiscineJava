package edu.Roma42.classes;

import java.util.StringJoiner;

public class Car {
	String brand;
	String model;
	int km;

	public Car() {
		this.brand = "default brand";
		this.model = "default model";
		this.km = 0;
	}

	public Car(String brand, String model, int km) {
		this.brand = brand;
		this.model = model;
		this.km = km;
	}

	public int IncreaseKm(int km) {
		this.km += km;
		return (this.km);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
		.add("brand='" + brand + "'")
		.add("model='" + model + "'")
		.add("km=" + km)
		.toString();
	}
}
