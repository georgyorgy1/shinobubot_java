package org.georgyorgy1.shinobu.managers;

import java.io.FileReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonManager
{
    private Logger logger;
    private JsonReader reader;

    public JsonManager()
    {
        this.logger = LoggerFactory.getLogger(JsonManager.class.getName());
        
        try
        {
            this.reader = Json.createReader(new FileReader("files/config.json"));
        }
        
        catch (IOException exception)
        {
            this.logger.error(exception.toString());
        }
        
        catch (JsonException exception)
        {
            this.logger.error(exception.toString());
        }
    }
    
    public JsonObject getJsonObject()
    {
        return reader.readObject();
    }
}