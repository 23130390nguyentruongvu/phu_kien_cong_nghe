package vn.edu.hcmuaf.fit.pkcn.utils;

import com.google.gson.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonProvider {

    private static final Gson INSTANCE;

    static {
        INSTANCE = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))

                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))

                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }

    public static Gson getGson() {
        return INSTANCE;
    }
}