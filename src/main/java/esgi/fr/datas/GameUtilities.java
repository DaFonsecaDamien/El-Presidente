package esgi.fr.datas;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import esgi.fr.GameParameters.Season;
import esgi.fr.Scenario;
import esgi.fr.utilities.Event.Choice;
import esgi.fr.utilities.Event.Effect;
import esgi.fr.utilities.Event.Event;
import esgi.fr.utilities.Faction.Faction;
import esgi.fr.utilities.Faction.NameFaction;

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
     * Return Map
     *
     * @param files directory where to get the jsons
     */
    public static Map<Integer, Map<String, String>> getScenarioName(List<File> files) throws FileNotFoundException {
        Map<Integer, Map<String, String>> indexPathString = new HashMap<>();
        int i = 1;
        for (File file : files) {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();
            String name = parseScenarioName(fileObject);
            Map<String, String> pathString = new HashMap<>();
            pathString.put(file.getPath(), name);
            indexPathString.put(i, pathString);
            i++;
        }
        return indexPathString;
    }

    /**
     * Return a Scenario from the Json File (Object)
     *
     * @param path path to find the json selected by the user
     * @return Scenario gameScenario
     */
    public static Scenario parseJsonToObject(String path) {
        File input = new File(path);
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extract the gameStartParameters and parse it
            JsonObject gameParametersJson = fileObject.get("gameStartParameters").getAsJsonObject();
            JsonObject startParametersJson = gameParametersJson.get("NORMAL").getAsJsonObject();

            // Extract Faction with parameters
            ArrayList<Faction> gameFactions = parseFaction(startParametersJson);

            // Create an instance of gameScenario
            Scenario gameScenario = new Scenario(
                    parseEvent(fileObject, "events"),
                    parseScenarioName(fileObject),
                    parseScenarioStory(fileObject),
                    parseAgriculturePercentage(startParametersJson),
                    parseIndustryPercentage(startParametersJson),
                    parseTreasury(startParametersJson),
                    parseFoodUnits(startParametersJson),
                    gameFactions,
                    1,
                    Season.WINTER);

            return gameScenario;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Votre programme est corrompu impossible d'établir le lien");
            System.exit(1);
        }
        return null;
    }

    /**
     * Return the name of the scenario
     *
     * @param fileObject JsonObject with the json inside
     * @return String name
     */
    public static String parseScenarioName(JsonObject fileObject) {
        String name = fileObject.get("name").getAsString();
        return name;
    }

    /**
     * Return the scenario story
     *
     * @param fileObject JsonObject with the json inside
     * @return String story
     */
    public static String parseScenarioStory(JsonObject fileObject) {
        String story = fileObject.get("story").getAsString();
        return story;
    }

    /**
     * Return the percentage of the Agriculture
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int agriculturePercentage
     */
    public static int parseAgriculturePercentage(JsonObject startParametersJson) {
        int agriculturePercentage = startParametersJson.get("agriculturePercentage").getAsInt();
        return agriculturePercentage;
    }

    /**
     * Return the percentage of the Industry
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int industryPercentage
     */
    public static int parseIndustryPercentage(JsonObject startParametersJson) {
        int industryPercentage = startParametersJson.get("industryPercentage").getAsInt();
        return industryPercentage;
    }

    /**
     * Return the number of Treasury
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int treasury
     */
    public static int parseTreasury(JsonObject startParametersJson) {
        int treasury = startParametersJson.get("treasury").getAsInt();
        return treasury;
    }

    /**
     * Return the number of Food Units
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int foodUnits
     */
    public static int parseFoodUnits(JsonObject startParametersJson) {
        int foodUnits = startParametersJson.get("foodUnits").getAsInt();
        return foodUnits;
    }

    /**
     * Return an ArrayList of Faction
     *
     * @param fileObject JsonObject with the special Element for the Faction
     * @return ArrayList factions
     */
    public static ArrayList<Faction> parseFaction(JsonObject fileObject) {
        JsonObject factionsObject = fileObject.get("factions").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = factionsObject.entrySet();
        //ARRAY of Faction
        ArrayList<Faction> factions = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonObject actualFactionElement = entry.getValue().getAsJsonObject();
            int satisfactionPercentage = actualFactionElement.get("satisfactionPercentage").getAsInt();
            int numberOfPartisans = actualFactionElement.get("numberOfPartisans").getAsInt();
            // Check the right Faction
            NameFaction nameFaction = null;
            for (NameFaction faction : NameFaction.values()) {
                if (faction.getValue().equals(entry.getKey())) {
                    nameFaction = faction;
                }
            }
            //CREATE INSTANCE OF FACTION
            Faction faction = new Faction(nameFaction, satisfactionPercentage, numberOfPartisans);
            factions.add(faction);
        }
        return factions;
    }

    /**
     * Return an ArrayList of Event
     *
     * @param fileObject JsonObject with the special Element for the Event
     * @return ArrayList events
     */
    public static ArrayList<Event> parseEvent(JsonObject fileObject, String nameObjectJson) {
        JsonArray jsonArrayOfEvent = fileObject.get(nameObjectJson).getAsJsonArray();
        ArrayList<Event> events = new ArrayList<>();
        for (JsonElement eventElement : jsonArrayOfEvent) {
            //Get the jsonObject
            JsonObject eventJsonObject = eventElement.getAsJsonObject();
            int id = eventJsonObject.get("id").getAsInt();
            //Extract data
            String name = eventJsonObject.get("name").getAsString();
            // Get all Choices
            ArrayList<Choice> listChoice = parseChoice(eventJsonObject);
            // CREATE INSTANCE OF EVENT
            Event event = new Event(id,listChoice,name);
            events.add(event);
        }
        return events;
    }

    /**
     * Return an ArrayList of Choice
     *
     * @param fileObject JsonObject with the speacial Element for the choice
     * @return ArrayList choices
     */
    public static ArrayList<Choice> parseChoice(JsonObject fileObject) {
        JsonArray jsonArrayOfChoice = fileObject.get("choices").getAsJsonArray();
        ArrayList<Choice> choices = new ArrayList<>();
        for (JsonElement choiceElement : jsonArrayOfChoice) {
            Integer relatedEvent = null;
            //Get the jsonObject
            JsonObject choiceJsonObject = choiceElement.getAsJsonObject();
            //Extract data
            String name = choiceJsonObject.get("choice").getAsString();
            // Get all Effects
            ArrayList<Effect> listEffect = parseEffects(choiceJsonObject);
            // Get related Event of the choice
            if (choiceJsonObject.has("relatedEvents")) {
                relatedEvent = choiceJsonObject.get("relatedEvents").getAsInt();
            }
            // CREATE INSTANCE OF CHOICE
            Choice choice = new Choice(listEffect, name, relatedEvent);
            choices.add(choice);
        }
        return choices;
    }

    /**
     * Return an ArrayList of Effect
     *
     * @param fileObject JsonObject with the special Element for the Effect
     * @return ArrayList effects
     */
    private static ArrayList<Effect> parseEffects(JsonObject fileObject) {
        JsonArray jsonArrayOfEffect = fileObject.get("effects").getAsJsonArray();
        ArrayList<Effect> effects = new ArrayList<>();
        for (JsonElement effectElement : jsonArrayOfEffect) {
            //Get the jsonObject
            JsonObject effectJsonObject = effectElement.getAsJsonObject();
            //Extract data
            setTypeAction(effectJsonObject, "actionOnFaction", effects);
            setTypeAction(effectJsonObject, "actionOnFactor", effects);
            if (!effectJsonObject.has("actionOnFactor") && !effectJsonObject.has("actionOnFaction")) {
                int effectPartisans = effectJsonObject.get("partisans").getAsInt();
                HashMap<String, Integer> action = new HashMap<>();
                action.put("partisans", effectPartisans);
                Effect effect = new Effect(null, action);
                effects.add(effect);
            }
        }
        return effects;
    }

    /**
     * Return an ArrayList of Effect
     *
     * @param effectJsonObject,strAction,effects with the special Element for the Effect
     * @return ArrayList effects
     */
    public static void setTypeAction(JsonObject effectJsonObject, String strAction, ArrayList<Effect> effects) {
        if (effectJsonObject.has(strAction)) {
            JsonObject effectActionOnFactor = effectJsonObject.get(strAction).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = effectActionOnFactor.entrySet();
            for (Map.Entry<String, JsonElement> entry : entries) {
                HashMap<String, Integer> action = new HashMap<>();
                action.put(entry.getKey(), entry.getValue().getAsInt());
                Effect effect = new Effect(strAction, action);
                effects.add(effect);
            }
        }
    }

}


