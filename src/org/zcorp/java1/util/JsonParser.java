package org.zcorp.java1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.zcorp.java1.model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<Section>())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static void write(Object object, Writer writer) {
        GSON.toJson(object, writer);
    }
}