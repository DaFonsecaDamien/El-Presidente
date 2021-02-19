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
}
