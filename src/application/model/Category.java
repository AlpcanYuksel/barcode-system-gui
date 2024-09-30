package application.model;

import java.util.UUID;

// Define a class to hold category data
public class Category {
	private String name;
	private UUID id;

	public Category(String name, UUID id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}
}