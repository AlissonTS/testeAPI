package api.model;

import java.util.ArrayList;
import java.util.List;

public class JsonShow {
    private List<String> keyWords = new ArrayList();
    private List<Recipie> recipies = new ArrayList();

    public List<String> getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<Recipie> getRecipies() {
        return this.recipies;
    }

    public void setRecipies(List<Recipie> recipies) {
        this.recipies = recipies;
    }
}