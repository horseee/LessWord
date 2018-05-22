package app10e.validator;

import java.util.ArrayList;
import java.util.List;

import app10e.form.ProductForm;


public class ProductValidator {
	
	public List<String> validate(ProductForm productForm) {
		List<String> errors = new ArrayList<String>();
		String name = productForm.getName();
		if (name == null || name.trim().isEmpty()) {
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
