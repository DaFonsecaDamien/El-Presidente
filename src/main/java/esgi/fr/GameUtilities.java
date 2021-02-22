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
     * Return a Scenario from the Json File (Object)
     *
     * @param path path to find the json selected by the user
     * @return Scenario gameScenario
     */
    public static Scenario parseJsonToObject(String path){
        File input = new File(path);
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extract Global Information of the scenario
            parseScenarioName(fileObject);
            parseScenarioStory(fileObject);

            // Extract the gameStartParameters and parse it
            JsonObject gameParametersJson = fileObject.get("gameStartParameters").getAsJsonObject();
            JsonObject startParametersJson = gameParametersJson.get("NORMAL").getAsJsonObject();

            parseAgriculturePercentage(startParametersJson);
            parseIndustryPercentage(startParametersJson);
            parseTreasury(startParametersJson);
            parseFoodUnits(startParametersJson);

            // Extract Faction with parameters
            ArrayList<Faction> gameFactions = parseFaction(startParametersJson);
            ListFaction listGameFaction = new ListFaction(gameFactions);

            // Create an instance of gameScenario
            Scenario gameScenario = new Scenario(
                    parseEvent(fileObject, "events"),
                    parseScenarioName(fileObject),
                    parseScenarioStory(fileObject),
                    parseAgriculturePercentage(startParametersJson),
                    parseIndustryPercentage(startParametersJson),
                    parseTreasury(startParametersJson),
                    parseFoodUnits(startParametersJson),
                    listGameFaction);

            return gameScenario;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return the name of the scenario
     *
     * @param fileObject JsonObject with the json inside
     * @return String name
     */
    public static String parseScenarioName(JsonObject fileObject){
        String name = fileObject.get("name").getAsString();
        return name;
    }

    /**
     * Return the scenario story
     *
     * @param fileObject JsonObject with the json inside
     * @return String story
     */
    public static String parseScenarioStory(JsonObject fileObject){
        String story = fileObject.get("story").getAsString();
        return story;
    }

    /**
     * Return the percentage of the Agriculture
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int agriculturePercentage
     */
    public static int parseAgriculturePercentage(JsonObject startParametersJson){
        int agriculturePercentage = startParametersJson.get("agriculturePercentage").getAsInt();
        return agriculturePercentage;
    }

    /**
     * Return the percentage of the Industry
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int industryPercentage
     */
    public static int parseIndustryPercentage(JsonObject startParametersJson){
        int industryPercentage = startParametersJson.get("industryPercentage").getAsInt();
        return industryPercentage;
    }

    /**
     * Return the number of Treasury
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int treasury
     */
    public static int parseTreasury(JsonObject startParametersJson){
        int treasury = startParametersJson.get("treasury").getAsInt();
        return treasury;
    }

    /**
     * Return the number of Food Units
     *
     * @param startParametersJson JsonObject with the special Element of game start parameters
     * @return int foodUnits
     */
    public static int parseFoodUnits(JsonObject startParametersJson){
        int foodUnits = startParametersJson.get("foodUnits").getAsInt();
        return foodUnits;
    }

    /**
     * Return an ArrayList of Faction
     *
     * @param fileObject JsonObject with the special Element for the Faction
     * @return ArrayList factions
     */
    public static ArrayList<Faction> parseFaction(JsonObject fileObject){
        JsonObject factionsObject = fileObject.get("factions").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = factionsObject.entrySet();
        //ARRAY of Faction
        ArrayList<Faction> factions = new ArrayList<>();

        for(Map.Entry<String, JsonElement> entry: entries) {
            JsonObject actualFactionElement = entry.getValue().getAsJsonObject();
            int satisfactionPercentage = actualFactionElement.get("satisfactionPercentage").getAsInt();
            int numberOfPartisans = actualFactionElement.get("numberOfPartisans").getAsInt();
            // Check the right Faction
            NameFaction nameFaction;
            switch (entry.getKey()){
                case "CAPITALISTS":
                    nameFaction = NameFaction.CAPITALISTE;
                    break;
                case "COMMUNISTS":
                    nameFaction = NameFaction.COMMUNISTE;
                    break;
                case "LIBERALS":
                    nameFaction = NameFaction.LIBERAU;
                    break;
                case "RELIGIOUS":
                    nameFaction = NameFaction.RELIGIEU;
                    break;
                case "MILITARISTS":
                    nameFaction = NameFaction.MILITARISTE;
                    break;
                case "ECOLOGISTS":
                    nameFaction = NameFaction.ECOLOGISTE;
                    break;
                case "NATIONALISTS":
                    nameFaction = NameFaction.NATIONALISTE;
                    break;
                case "LOYALISTS":
                    nameFaction = NameFaction.LOYALISTE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + entry.getKey());
            }
            //CREATE INSTANCE OF FACTION
            Faction faction = new Faction(nameFaction,satisfactionPercentage,numberOfPartisans);
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
    public static ArrayList<Event> parseEvent(JsonObject fileObject, String nameObjectJson){
        JsonArray jsonArrayOfEvent = fileObject.get(nameObjectJson).getAsJsonArray();
        ArrayList<Event> events = new ArrayList<>();
        for(JsonElement eventElement : jsonArrayOfEvent){
            //Get the jsonObject
            JsonObject eventJsonObject = eventElement.getAsJsonObject();
            //Extract data
            String name = eventJsonObject.get("name").getAsString();
            // Get all Choices
            ArrayList<Choice> listChoice = parseChoice(eventJsonObject);
            // CREATE INSTANCE OF EVENT
            Event event = new Event(listChoice,name);
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
    public static ArrayList<Choice> parseChoice(JsonObject fileObject){
        JsonArray jsonArrayOfChoice = fileObject.get("choices").getAsJsonArray();
        ArrayList<Choice> choices = new ArrayList<>();
        for(JsonElement choiceElement : jsonArrayOfChoice){
            ArrayList<Event> relatedEvent = null;
            //Get the jsonObject
            JsonObject choiceJsonObject = choiceElement.getAsJsonObject();
            //Extract data
            String name = choiceJsonObject.get("choice").getAsString();
            // Get all Effects
            ArrayList<Effect> listEffect = parseEffects(choiceJsonObject);
            // Get related Event of the choice
            if(choiceJsonObject.has("relatedEvents")){
                relatedEvent = parseEvent(choiceJsonObject, "relatedEvents");
            }
            // CREATE INSTANCE OF CHOICE
            Choice choice = new Choice(listEffect,name,relatedEvent);
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
        for(JsonElement effectElement : jsonArrayOfEffect) {
            //Get the jsonObject
            JsonObject effectJsonObject = effectElement.getAsJsonObject();
            //Extract data
            if(effectJsonObject.has("actionOnFaction")){
                String typeAction = "actionOnFaction";
                JsonObject effectActionOnFaction = effectJsonObject.get("actionOnFaction").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = effectActionOnFaction.entrySet();
                for(Map.Entry<String, JsonElement> entry: entries) {
                    HashMap<String,Integer> action = new HashMap<>();
                    action.put(entry.getKey(),entry.getValue().getAsInt());
                    Effect effect = new Effect(typeAction,action);
                    effects.add(effect);
                }
            }
            if(effectJsonObject.has("actionOnFactor")){
                String typeAction = "actionOnFactor";
                JsonObject effectActionOnFactor = effectJsonObject.get("actionOnFactor").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = effectActionOnFactor.entrySet();
                for(Map.Entry<String, JsonElement> entry: entries) {
                    HashMap<String,Integer> action = new HashMap<>();
                    action.put(entry.getKey(),entry.getValue().getAsInt());
                    Effect effect = new Effect(typeAction,action);
                    effects.add(effect);
                }
            }
            if(!effectJsonObject.has("actionOnFactor") && !effectJsonObject.has("actionOnFaction")){
                int effectPartisans = effectJsonObject.get("partisans").getAsInt();
                HashMap<String,Integer> action = new HashMap<>();
                action.put("partisans",effectPartisans);
                Effect effect = new Effect(null,action);
                effects.add(effect);
            }
        }
        return effects;
    }
}
