package edu.Roma42.models;

import java.util.*;

public class Product {
    private Long id;
    private String name;
    private double price;

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	@Override
	public boolean equals(Object product) {

		if (this == product) {
			return (true);
		}

		if (product == null || getClass() != product.getClass()) {
			return (false);
		}

		Product tmp = (Product) product;

		if (this.id == tmp.getId() && this.name.equals(tmp.getName()) && this.name.equals(tmp.getPrice())) {
			return (true);
		}
		return (false);
	}

	@Override
	public int hashCode() {
		return (Objects.hash(this.id, this.name, this.price));
	}

	@Override
	public String toString() {
		return ("Product id : " + this.id + ", name : " + this.name + ", price : " + this.price);
	}
}
