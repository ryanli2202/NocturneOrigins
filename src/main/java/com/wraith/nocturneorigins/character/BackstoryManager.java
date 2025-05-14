package com.wraith.nocturneorigins.character;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.util.*;

public class BackstoryManager {

    public static class Backstory {
        public String id;
        public String title;
        public String text;
    }

    private static final List<Backstory> backstories = new ArrayList<>();

    public static void load() {
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(BackstoryManager.class.getResourceAsStream("/assets/nocturneorigins/data/backstories.json")))) {

            Gson gson = new Gson();
            Map<String, List<Backstory>> parsed = gson.fromJson(reader, new TypeToken<Map<String, List<Backstory>>>() {}.getType());
            backstories.clear();
            if (parsed.containsKey("backstories")) {
                backstories.addAll(parsed.get("backstories"));
            }} catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Backstory> getBackstories() {
        return backstories;
    }

    public static Backstory getById(String id) {
        return backstories.stream().filter(b -> b.id.equals(id)).findFirst().orElse(null);
    }
}
