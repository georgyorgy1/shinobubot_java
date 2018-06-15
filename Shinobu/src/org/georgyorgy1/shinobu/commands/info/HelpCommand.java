package org.georgyorgy1.shinobu.commands.info;

import java.io.FileReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class HelpCommand extends Command
{
    public HelpCommand()
    {
        this.name = "commands";
        this.help = "This help page";
        this.aliases = new String[]{"cmds", "cmd", "c", "help"};
    }

    @Override
    protected void execute(CommandEvent event)
    {
        JsonReader reader = null;
        final Logger logger = LoggerFactory.getLogger(HelpCommand.class.getName());
        
        //Open JSON file
        try
        {
            reader = Json.createReader(new FileReader("files/help.json"));
        }
        
        catch (IOException exception)
        {
            logger.error(exception.toString());
        }
        
        //Get objects
        JsonObject object = reader.readObject();
        
        //Close reader
        reader.close();
        
        //Get string
        String helpString = object.getString("help_string");

        //reply
        event.reply(helpString);
    }
}
