package com.lsdx.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyGson {
    
    private static final MyGson instance = new MyGson();
    
    private final Gson gson = toBuilderGson();
    
    private MyGson(){
        
    }
    
    public String toJson(Object o){
        if (o == null) {
            return null;
        }
        return gson.toJson(o);
    }
    
    public <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }
    
    public <T> T fromJson(String json, Type typeOfT){
        return gson.fromJson(json, typeOfT);  
    }
    
    public <T> T fromJson(JsonReader reader, Type collectionType) {
        return gson.fromJson(reader, collectionType);
    }
    

    public <T> T fromJson(JsonElement el, Class<T> classOfT) {
        return gson.fromJson(el, classOfT);
    }
   
    
    public static MyGson getInstance(){
       return instance;
    }
    
    private Gson toBuilderGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.registerTypeAdapter(Date.class,
                new JsonDeserializer<Date>() {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    @Override
                    public Date deserialize(final JsonElement json,
                            final Type typeOfT,
                            final JsonDeserializationContext context)
                            throws JsonParseException {
                        try {
                            return df.parse(json.getAsString());
                        } catch (ParseException e) {
                        }
                        return null;
                    }

                });
        gsonBuilder.registerTypeAdapter(Integer.class,
                new JsonDeserializer<Integer>() {

                    @Override
                    public Integer deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context)
                            throws JsonParseException {
                        try {
                            return Integer.parseInt(json.getAsString());
                        } catch (Exception e) {
                            return null;
                        }
                    }
                });
        return gsonBuilder.create();
    }


 
}
