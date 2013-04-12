package com.diphot.siu.Json;

import java.lang.reflect.Type;

import com.diphot.siuweb.shared.dtos.InterfaceDTO;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonAdapter implements JsonSerializer<InterfaceDTO>, JsonDeserializer<InterfaceDTO> {

  private static final String CLASSNAME = "CLASSNAME";
  private static final String INSTANCE  = "INSTANCE";
  private static final String ACTIONLABEL = "ACTION";
  private ACTION action;
  public enum ACTION {PUT,GET,UPDATE,DELETE,LIST,QUERY,NONE};
  
  public JsonAdapter(ACTION action) {
    this.action = action;
  }

  public JsonAdapter(){
    this.action = ACTION.NONE;
  }
  
  public ACTION getAction(){
    return this.action;
  }
  
  @Override
  public JsonElement serialize(InterfaceDTO src, Type typeOfSrc, JsonSerializationContext context) {
     JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(ACTIONLABEL,action.toString());
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = context.serialize(src); 
        retValue.add(INSTANCE, elem);
        return retValue;
  }
  
  @Override
  public InterfaceDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
     JsonObject jsonObject =  json.getAsJsonObject();
        String className = ((JsonPrimitive) jsonObject.get(CLASSNAME)).getAsString();
        String actionString = ((JsonPrimitive) jsonObject.get(ACTIONLABEL)).getAsString();
        this.action = ACTION.valueOf(actionString);
        Class<?> klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);
  }
}
