import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// a product from GET /products/{id}. Only the fields the tests need.
// The API sends more fields (brand, category, image...), so the others are ignored.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String id;
    private String name;
    private double price;

    // Jackson needs an empty constructor and setters to fill the object from the JSON
    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
