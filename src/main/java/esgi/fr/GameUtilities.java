package esgi.fr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameUtilities {

    /**
     * Return the list of json from a directory (File object)
     *
     * @param dir directory where to get the jsons
     */
    public static List<File> allJsonFromDir(File dir) {
        assert null != dir;
        return Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(file -> {
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
    public static JsonObject parseJsonToObject(String path){
        // parse le Json du scenario grâce au path en paramètre
        String json = "";
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        return convertedObject;
    }
    
//    String pathToScenariosDir = ".\\src\\ressources\\scenarios";
//    List<File> scenariosJson = GameUtilities.allJsonFromDir(new File(pathToScenariosDir));
//    System.out.println(scenariosJson);

}
