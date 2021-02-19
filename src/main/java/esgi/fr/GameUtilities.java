package esgi.fr;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class GameUtilities {

    /**
     * Return the list of json from a directory (File object)
     *
     * @param fileDir directory where to get the jsons
     */
    public static List<File> allJsonFromDir(File fileDir) {
        assert null != fileDir;
        return Arrays.stream(Objects.requireNonNull(fileDir.listFiles())).filter(file -> {
            String name = file.getName();
            int i = name.lastIndexOf('.');
            return (i > 0) && name.substring(i + 1).equals("json");
        }).collect(Collectors.toList());
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param path path to find the json selected by the user
     */
    public static void parseJsonToObject(String path){
        // parse le Json du scenario grâce au path en paramètre
        File input = new File(path);
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extracting the basic fields
            String name = fileObject.get("name").getAsString();
            String story = fileObject.get("story").getAsString();
            System.out.println(name + "\n");
            System.out.println(story + "\n");

            //JsonObject eventJson = fileObject.get("events").getAsJsonObject();
            parseEvent(fileObject);

            JsonObject gameParametersJson = fileObject.get("gameStartParameters").getAsJsonObject();
            JsonObject startParametersJson = gameParametersJson.get("NORMAL").getAsJsonObject();

            parseStartParameters(startParametersJson);

            JsonObject factionJson = startParametersJson.get("factions").getAsJsonObject();
            parseFaction(factionJson);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param startParametersJson path to find the json selected by the user
     */
    public static void parseStartParameters(JsonObject startParametersJson){
        int agriculturePercentage = startParametersJson.get("agriculturePercentage").getAsInt();
        int industryPercentage = startParametersJson.get("industryPercentage").getAsInt();
        int treasury = startParametersJson.get("treasury").getAsInt();
        int foodUnits = startParametersJson.get("foodUnits").getAsInt();
        System.out.println(agriculturePercentage);
        System.out.println(industryPercentage);
        System.out.println(treasury);
        System.out.println(foodUnits);
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param factionJson path to find the json selected by the user
     */
    public static void parseFaction(JsonObject factionJson){

    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject fileObject global
     */
    public static void parseEvent(JsonObject fileObject){
        JsonArray jsonArrayOfEvent = fileObject.get("events").getAsJsonArray();
        ArrayList<Event> events = new ArrayList<>();
        for(JsonElement eventElement : jsonArrayOfEvent){
            //Get the jsonObject
            JsonObject eventJsonObject = eventElement.getAsJsonObject();
            //Extract data
            String name = eventJsonObject.get("name").getAsString();
            System.out.println(name);
            // Get all Choices
            ArrayList<Choice> listChoice = parseChoice(eventJsonObject);
        }

    }
    
    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject eventJson Object
     * @return
     */
    public static ArrayList<Choice> parseChoice(JsonObject fileObject){
        JsonArray jsonArrayOfChoice = fileObject.get("choices").getAsJsonArray();
        ArrayList<Choice> choices = new ArrayList<>();
        for(JsonElement choiceElement : jsonArrayOfChoice){
            //Get the jsonObject
            JsonObject choiceJsonObject = choiceElement.getAsJsonObject();
            //Extract data
            String choice = choiceJsonObject.get("choice").getAsString();
            System.out.println(choice);
            // Get all Effects
            ArrayList<Effect> listEffect = parseEffects(choiceJsonObject);
        }

        return choices;
    }
}
