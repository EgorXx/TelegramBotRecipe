package bot.telegram.recipes.entities;

public class Ingredient {
    private Integer id;
    private String title;
    private Double count;
    private Unit unit;

    public Ingredient(Integer id, String title, Double count, Unit unit) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.unit = unit;
    }

    public Ingredient(String title) {
        this.title = title;
    }

    public Ingredient() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getCount() {
        return count;
    }

    public Unit getUnit() {
        return unit;
    }
}
