package app10d.validator;

import java.util.ArrayList;
import java.util.List;

import app10d.form.ProductForm;


public class ProductValidator {
	
	public List<String> validate(ProductForm productForm) {
		List<String> errors = new ArrayList<String>();
		String productName = productForm.getName();
		if (productName == null || productName.trim().isEmpty()) {
			errors.add("Product name must have a value");
		}
		String price = productForm.getPrice();
		if (price == null || price.trim().isEmpty()) {
			errors.add("Product must have a price");
		} else {
			try {
				Float.parseFloat(price);
			} catch (NumberFormatException e) {
				errors.add("Invalid price value");
			}
		}
		return errors;
	}

}
