package api.controller;

import api.model.JsonShow;
import api.model.Recipie;
import api.util.ManipulateString;
import api.util.ReadJSON;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {
    @RequestMapping(value = "recipies", method = RequestMethod.GET)
    public ModelAndView recipies(HttpServletRequest rq) {
        ModelAndView mv = new ModelAndView("home");
        String recipies = rq.getParameter("i");
        if (recipies != null) {
            System.out.println("Recipies: " + recipies);

            String[] onlyRecipies = recipies.split("&");
            recipies = onlyRecipies[0];

            String[] arrayRecipies = recipies.split(",");
            if (arrayRecipies.length <= 3) {
                String url = "http://www.recipepuppy.com/api/?i=";
                url = url + recipies;

                String jsonString = null;
                try {
                    new ReadJSON();
                    jsonString = ReadJSON.readJsonFromUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (jsonString != null) {
                    JsonShow jsonShow = new JsonShow();
                    for (String keyWord : arrayRecipies) {
                        List<String> keyWords = jsonShow.getKeyWords();
                        keyWords.add(keyWord);
                        jsonShow.setKeyWords(keyWords);
                    }
                    JSONObject recipiesJSON = new JSONObject(jsonString);
                    JSONArray recipiesArray = recipiesJSON.getJSONArray("results");
                    if (recipiesArray != null) {
                        for (int i = 0; i < recipiesArray.length(); i++) {
                            ManipulateString manipulateString = new ManipulateString();
                            String title = recipiesArray.getJSONObject(i).getString("title");
                            title = manipulateString.replaceEscapeString(title);

                            String ingredients = recipiesArray.getJSONObject(i).getString("ingredients");
                            ingredients = manipulateString.replaceEscapeString(ingredients);
                            String[] arrayIngredients = ingredients.split(",");

                            String link = recipiesArray.getJSONObject(i).getString("href");
                            link = manipulateString.replaceEscapeString(link);

                            Recipie recipie = new Recipie();
                            recipie.setTitle(title);
                            recipie.setLink(link);
                            List<String> ingredientsList = recipie.getIngredients();
                            for (String ingredientsReturn : arrayIngredients) {
                                ingredientsList.add(ingredientsReturn);
                            }
                            recipie.setIngredients(ingredientsList);

                            String urlAPIGif = "http://api.giphy.com/v1/gifs/search?";
                            String search = title.replaceAll(" ", "+");
                            String keyAPI = "dc6zaTOxFJmzC";
                            String limit = "1";

                            String urlConcat = urlAPIGif + "q=" + search + "&api_key=" + keyAPI + "&limit=" + limit;

                            String jsonStringGif = null;
                            try {
                                jsonStringGif = new ReadJSON().readJsonFromUrl(urlConcat);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String urlGif;
                            if (jsonStringGif != null) {
                                JSONObject gifJSON = new JSONObject(jsonStringGif);
                                JSONArray dataArray = gifJSON.getJSONArray("data");
                                JSONObject images = (JSONObject) dataArray.getJSONObject(0).get("images");
                                JSONObject original = (JSONObject) images.get("original");
                                urlGif = original.getString("url");
                                String[] urlGifExtension = urlGif.split(".gif");
                                urlGif = urlGifExtension[0] + ".gif";
                            } else {
                                urlGif = "No API key found for this recipie title";
                            }
                            recipie.setGif(urlGif);

                            List<Recipie> recipiesList = jsonShow.getRecipies();
                            recipiesList.add(recipie);
                            jsonShow.setRecipies(recipiesList);
                        }
                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(jsonShow);
                    System.out.println("JSON gerado: " + json);
                    mv.addObject("jsonResultado", json);
                }
            }
        }
        return mv;
    }
}