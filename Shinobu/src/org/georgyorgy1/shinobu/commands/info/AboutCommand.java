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

public class AboutCommand extends Command
{
    public AboutCommand()
    {
        this.name = "about";
        this.help = "get information about the bot";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        Logger logger = LoggerFactory.getLogger(AboutCommand.class.getName());
        
        try 
        {
            try 
            {
                object = (JSONObject)parser.parse(new FileReader("files/config.json"));
            } 
            
            catch (IOException ex) 
            {
                logger.error(ex.toString());                
            }
        }
        
        catch (ParseException ex)
        {
            logger.error(ex.toString());
        }
        
        String botName = (String)object.get("bot_name");
        String author = "Author: georgyorgy1 / Darth Squidward";
        String build = "Build: " + (String)object.get("build");
        String jdkVersion = "JDK Version: " + System.getProperty("java.version");
        String memory = "Memory: " + Long.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " / " + Long.toString(Runtime.getRuntime().totalMemory() / 1048576) + " MB";
        String shard = "Shard: " + ce.getJDA().getShardInfo();
        String sourceCode = "Source Code: " + "https://github.com/georgyorgy1/shinobubot_java";
        String message = botName + "\n" + "\n" + author + "\n" + build + "\n" + jdkVersion + "\n" + memory + "\n" + shard + "\n" + sourceCode;
        
        ce.reply(message);
    }
}
