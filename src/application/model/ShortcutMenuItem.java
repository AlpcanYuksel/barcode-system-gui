package application.model;

import java.util.UUID;

public class ShortcutMenuItem {
	private UUID id;

	private UUID shortcutMenuId;

	private String productName;

	private String barcode;

	public ShortcutMenuItem() {
		super();
	}

	public ShortcutMenuItem(UUID id, UUID shortcutMenuId, String productName, String barcode) {
		super();
		this.id = id;
		this.shortcutMenuId = shortcutMenuId;
		this.productName = productName;
		this.barcode = barcode;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getShortcutMenuId() {
		return shortcutMenuId;
	}

	public void setShortcutMenuId(UUID shortcutMenuId) {
		this.shortcutMenuId = shortcutMenuId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}
