package application.model;

import java.util.UUID;

// Define a class to hold category data
public class Product {
	private UUID id;

	private String barcode;

	private String productName;

	private double quantity;

	private double unitPrice;

	public Product() {
		super();
	}

	public Product(UUID id, String barcode, String productName, double quantity, double unitPrice) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.productName = productName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public Product(UUID id, String productName) {
		super();
		this.productName = productName;
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

}