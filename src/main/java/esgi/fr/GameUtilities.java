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
     * @return
     */
    public static Scenario parseJsonToObject(String path){
        // parse le Json du scenario grâce au path en paramètre
        File input = new File(path);
        try{
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // Extract Global Information of the scenario
//            parseGlobalInformation(fileObject);
            parseScenarioName(fileObject);
            parseScenarioStory(fileObject);


            // Extract startParameters
            JsonObject gameParametersJson = fileObject.get("gameStartParameters").getAsJsonObject();
            JsonObject startParametersJson = gameParametersJson.get("NORMAL").getAsJsonObject();
//            parseStartParameters(startParametersJson);
            parseAgriculturePercentage(startParametersJson);
            parseIndustryPercentage(startParametersJson);
            parseTreasury(startParametersJson);
            parseFoodUnits(startParametersJson);

            // Extract Events of the scenario
            ArrayList<Event> gameEvents = parseEvent(fileObject);

            // Extract Faction with parameters
            ArrayList<Faction> gameFactions = parseFaction(startParametersJson);
            ListFaction listGameFaction = new ListFaction(gameFactions);

            // Create ALL THE INSTANCE
            Scenario gameScenario = new Scenario(
                    parseEvent(fileObject),
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
     * @param fileObject JsonObject with the json
     */
    public static String parseScenarioName(JsonObject fileObject){
        // Extracting the basic fields
        String name = fileObject.get("name").getAsString();
//        System.out.println(name + "\n");
        return name;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject path to find the json selected by the user
     */
    public static String parseScenarioStory(JsonObject fileObject){
        // Extracting the basic fields
        String story = fileObject.get("story").getAsString();
//        System.out.println(story + "\n");
        return story;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param startParametersJson path to find the json selected by the user
     */
    public static int parseAgriculturePercentage(JsonObject startParametersJson){
        int agriculturePercentage = startParametersJson.get("agriculturePercentage").getAsInt();
//        System.out.println(agriculturePercentage);
        return agriculturePercentage;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param startParametersJson path to find the json selected by the user
     */
    public static int parseIndustryPercentage(JsonObject startParametersJson){
        int industryPercentage = startParametersJson.get("industryPercentage").getAsInt();
//        System.out.println(industryPercentage);
        return industryPercentage;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param startParametersJson path to find the json selected by the user
     */
    public static int parseTreasury(JsonObject startParametersJson){
        int treasury = startParametersJson.get("treasury").getAsInt();
//        System.out.println(treasury);
        return treasury;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param startParametersJson path to find the json selected by the user
     */
    public static int parseFoodUnits(JsonObject startParametersJson){
        int foodUnits = startParametersJson.get("foodUnits").getAsInt();
//        System.out.println(foodUnits);
        return foodUnits;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject path to find the json selected by the user
     */
    public static ArrayList<Faction> parseFaction(JsonObject fileObject){
        JsonObject factionsObject = fileObject.get("factions").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = factionsObject.entrySet();
        //ARRAY of Faction
        ArrayList<Faction> factions = new ArrayList<>();

        for(Map.Entry<String, JsonElement> entry: entries) {
            JsonObject actualFactionElement = entry.getValue().getAsJsonObject();
            String name = actualFactionElement.get("name").getAsString();
            int satisfactionPercentage = actualFactionElement.get("satisfactionPercentage").getAsInt();
            int numberOfPartisans = actualFactionElement.get("numberOfPartisans").getAsInt();
//            System.out.println("\t" + name + "\t"+satisfactionPercentage+"\t"+numberOfPartisans);
            NameFaction nameFaction = null;
//            for (NameFaction elementFaction : NameFaction.values()) {
//                if(name == elementFaction.label){
//
//                }
//            }

//             CREATE INSTANCE OF FACTION
            Faction faction = new Faction(nameFaction,satisfactionPercentage,numberOfPartisans);
            factions.add(faction);
        }
        return factions;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject fileObject global
     */
    public static ArrayList<Event> parseEvent(JsonObject fileObject){
        JsonArray jsonArrayOfEvent = fileObject.get("events").getAsJsonArray();
        ArrayList<Event> events = new ArrayList<>();
        for(JsonElement eventElement : jsonArrayOfEvent){
            //Get the jsonObject
            JsonObject eventJsonObject = eventElement.getAsJsonObject();
            //Extract data
            String name = eventJsonObject.get("name").getAsString();
//            System.out.println("\nNEW EVENT : "+name + "\n");
            // Get all Choices
            ArrayList<Choice> listChoice = parseChoice(eventJsonObject);
            // CREATE INSTANCE OF EVENT
            Event event = new Event(listChoice,name);
            events.add(event);
        }
        return events;
    }

    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject fileObject global
     */
    public static ArrayList<Event> parseRelatedEvent(JsonObject fileObject){
        JsonArray jsonArrayOfRelatedEvent = fileObject.get("relatedEvents").getAsJsonArray();
        ArrayList<Event> relatedEvents = new ArrayList<>();
        for(JsonElement eventElement : jsonArrayOfRelatedEvent){
            //Get the jsonObject
            JsonObject eventJsonObject = eventElement.getAsJsonObject();
            //Extract data
            String name = eventJsonObject.get("name").getAsString();
//            System.out.println("\nNEW RELATED EVENT : "+name + "\n");
            // Get all Choices
            ArrayList<Choice> listChoice = parseChoice(eventJsonObject);
            // CREATE INSTANCE OF EVENT
            Event relatedEvent = new Event(listChoice,name);
            relatedEvents.add(relatedEvent);
        }
        return relatedEvents;
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
            ArrayList<Event> relatedEvent = null;
            //Get the jsonObject
            JsonObject choiceJsonObject = choiceElement.getAsJsonObject();
            //Extract data
            String name = choiceJsonObject.get("choice").getAsString();
//            System.out.println(name);
            // Get all Effects
            ArrayList<Effect> listEffect = parseEffects(choiceJsonObject);
            // Get related Event of the choice
            if(choiceJsonObject.has("relatedEvents")){
//                System.out.println("\n ************RELATED EVENT************ \n");
                relatedEvent = parseRelatedEvent(choiceJsonObject);
            }
            // CREATE INSTANCE OF CHOICE
            Choice choice = new Choice(listEffect,name,relatedEvent);
            choices.add(choice);
        }
        return choices;
    }
    
    /**
     * Return a JsonObject from the Json File (Object)
     *
     * @param fileObject path to find the json selected by the user
     * @return
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
//                    System.out.println("\t Effet pour les " + entry.getKey() + " est de " + entry.getValue());
                    Effect effect = new Effect(typeAction,entry.getKey(),entry.getValue().getAsInt());
                    effects.add(effect);
                }
            }
            if(effectJsonObject.has("actionOnFactor")){
                String typeAction = "actionOnFactor";
                JsonObject effectActionOnFactor = effectJsonObject.get("actionOnFactor").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = effectActionOnFactor.entrySet();
                for(Map.Entry<String, JsonElement> entry: entries) {
//                    System.out.println("\t L'effet pour l'" + entry.getKey() + " est de " + entry.getValue());
                    Effect effect = new Effect(typeAction,entry.getKey(),entry.getValue().getAsInt());
                    effects.add(effect);
                }
            }
            if(!effectJsonObject.has("actionOnFactor") && !effectJsonObject.has("actionOnFaction")){
                String typeAction = null;
                int effectPartisans = effectJsonObject.get("partisans").getAsInt();
                Effect effect = new Effect(typeAction,"partisans",effectPartisans);
                effects.add(effect);
//                System.out.println("\t PARTISANS : "+effectPartisans);
            }
        }
        return effects;
    }
}
