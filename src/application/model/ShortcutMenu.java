package application.model;

import java.util.UUID;

public class ShortcutMenu {
	private String categoryName;
	private UUID id;

	public ShortcutMenu(String categoryName, UUID id) {
		this.categoryName = categoryName;
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return categoryName;
	}
}