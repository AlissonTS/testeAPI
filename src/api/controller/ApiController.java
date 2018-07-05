package api.controller;

import api.model.MessageAPI;
import api.model.JsonShow;
import api.model.Recipie;
import api.util.CheckConnection;
import api.util.ManipulateString;
import api.util.ReadJSON;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiController {
    @RequestMapping(value = "recipies", method = RequestMethod.GET)
    public void recipiesJSON(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        Gson gson = new Gson();
        try {
            PrintWriter out = response.getWriter();

            String recipies = request.getParameter("i");
            if (recipies != null) {
                System.out.println("Recipies: " + recipies);

                String[] onlyRecipies = recipies.split("&");
                recipies = onlyRecipies[0];

                String[] arrayRecipies = recipies.split(",");

                CheckConnection checkConnection = new CheckConnection();
                if (checkConnection.tryConnect()) {
                    if (arrayRecipies.length <= 3) {
                        String url = "http://www.recipepuppy.com/api/?i=";
                        url = url + recipies;

                        String jsonString = null;
                        try {
                            jsonString = new ReadJSON().readJsonFromUrl(url);
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
                                    String[] arrayIngredients = ingredients.split(", ");
                                    List<String> ingredientsArray = new ArrayList<>(Arrays.asList(arrayIngredients));
                                    Collections.sort(ingredientsArray);

                                    String link = recipiesArray.getJSONObject(i).getString("href");
                                    link = manipulateString.replaceEscapeString(link);

                                    Recipie recipie = new Recipie();
                                    recipie.setTitle(title);
                                    recipie.setLink(link);
                                    List<String> ingredientsList = recipie.getIngredients();
                                    for (String ingredientsReturn : ingredientsArray) {
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
                            String json = gson.toJson(jsonShow);
                            System.out.println("JSON gerado: " + json);
                            out.println(json); // Print do Json
                        } else {
                            MessageAPI messageAPI = new MessageAPI("Erro em consulta em API Recipe Puppy.");
                            out.println(gson.toJson(messageAPI)); // Print do Json
                        }
                    } else {
                        MessageAPI messageAPI = new MessageAPI("Mais de três ingredientes colocados para pesquisa.");
                        out.println(gson.toJson(messageAPI)); // Print do Json
                    }
                } else {
                    MessageAPI messageAPI = new MessageAPI("Sem conexão com internet.");
                    out.println(gson.toJson(messageAPI)); // Print do Json
                }
            } else {
                MessageAPI messageAPI = new MessageAPI("Sem ingredientes a ser pesquisados.");
                out.println(gson.toJson(messageAPI)); // Print do Json
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}