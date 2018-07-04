package api.model;

import java.util.ArrayList;
import java.util.List;

public class Recipie {
    private String title;
    private List<String> ingredients = new ArrayList();
    private String link;
    private String gif;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGif() {
        return this.gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }
}