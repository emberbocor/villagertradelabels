package io.github.emberbocor.villagertradelabels.config;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.emberbocor.villagertradelabels.VillagerTradeLabels;

public final class ClientConfig {
    private static final String FILE_NAME = VillagerTradeLabels.MODID + "-client.json";
    private static final String SHOW_LABELS = "showLabels";

    private static boolean loaded;
    private static boolean showLabels = true;

    private ClientConfig() {
    }

    public static boolean showLabels() {
        if (!loaded) {
            load();
        }
        return showLabels;
    }

    public static void setShowLabels(boolean show) {
        showLabels = show;
        loaded = true;
        save(JsonConfigFile.resolve(FILE_NAME));
    }

    private static void load() {
        loaded = true;
        Path file = JsonConfigFile.resolve(FILE_NAME);
        if (Files.notExists(file)) {
            save(file);
            return;
        }
        JsonConfigFile.read(file).ifPresent(json -> {
            JsonElement value = json.get(SHOW_LABELS);
            if (value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
                showLabels = value.getAsBoolean();
            } else {
                VillagerTradeLabels.LOGGER.warn("Invalid {} in {} (expected true or false), using true", SHOW_LABELS, file);
            }
        });
    }

    private static void save(Path file) {
        JsonObject json = new JsonObject();
        json.addProperty(SHOW_LABELS, showLabels);
        JsonConfigFile.write(file, json);
    }
}
