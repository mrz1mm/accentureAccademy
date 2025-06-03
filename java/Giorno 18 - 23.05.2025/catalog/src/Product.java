

public class Product  {

    private int id;
    private String name;
    private String category;

    public Product() {
        super();
        this.id = 0;
        this.name = "N/A";
        this.category = "N/A";
    }

    public Product(int id, String name, String category) {
        super();
        this.id = id;
        this.name = name;
        this.category = category;
    }


    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }


    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }


    // Metodi concreti
    public String showInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Category: ").append(category).append("\n");
        return sb.toString();
    }

}
