package app18a;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1000L;

	private String name;
    private String description;
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String execute() {
        return "success";
    }
}
