package org.georgyorgy1.shinobu.commands.info;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    protected void execute(CommandEvent ce)
    {
        String helpInfo = null;
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        Logger logger = LoggerFactory.getLogger(HelpCommand.class.getName());

        try
        {
            try 
            {
                object = (JSONObject) parser.parse(new FileReader("files/help.json"));
            } 
            
            catch (ParseException ex) 
            {
                logger.error(ex.toString());
            }
            
            helpInfo = object.get("help_string").toString();
            ce.reply(helpInfo);
        }

        catch (IOException exception)
        {
            logger.error(exception.toString(), exception);
        }
    }
}
