package esgi.fr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        return Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(x -> {
            String name = x.getName();
            int i = name.lastIndexOf('.');
            return (i > 0) && name.substring(i + 1).equals("json");
        }).collect(Collectors.toList());
    }

//    String pathToScenariosDir = ".\\src\\ressources\\scenarios";
//    List<File> scenariosJson = GameUtilities.allJsonFromDir(new File(pathToScenariosDir));
//    System.out.println(scenariosJson);

}
