package app10a.model;
import java.io.Serializable;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

public class Product implements Serializable {
    private static final long serialVersionUID = 748392348L;
	private String name;
    private String description;
    private float price;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}