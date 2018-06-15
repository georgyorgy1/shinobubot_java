package org.georgyorgy1.shinobu.commands.info;

import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
    
    private String getUptime()
    {
        //Get total uptime
        long longUptime = ManagementFactory.getRuntimeMXBean().getUptime();
        
        //Get days
        long days = TimeUnit.MILLISECONDS.toDays(longUptime);
        
        //Update total
        longUptime = longUptime - TimeUnit.DAYS.toMillis(days);
        
        //Get hours
        long hours = TimeUnit.MILLISECONDS.toHours(longUptime);
        
        //Update total
        longUptime = longUptime - TimeUnit.HOURS.toMillis(hours);
        
        //Get minutes
        long minutes = TimeUnit.MILLISECONDS.toMinutes(longUptime);
        
        //Update Total
        longUptime = longUptime - TimeUnit.MILLISECONDS.toMillis(minutes);
        
        //Get seconds
        long seconds = TimeUnit.MILLISECONDS.toSeconds(longUptime);
        
        //To make sure hours, minutes and seconds does not go beyond 24 (for hours) or 60 (for minutes and seconds)
        if (hours >= 24)
        {
            hours = hours % 24;
        }
        
        if (minutes >= 60 && hours >= 1)
        {
            minutes = minutes % 60;
        }
        
        if (seconds >= 60)
        {
            seconds = seconds % 60;
        }

        //Return uptime
        return Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes and " + Long.toString(seconds) + " seconds";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        JsonReader reader = null;
        final Logger logger = LoggerFactory.getLogger(HelpCommand.class.getName());
        
        //Open JSON file
        try
        {
            reader = Json.createReader(new FileReader("files/config.json"));
        }
        
        catch (IOException exception)
        {
            logger.error(null, exception);
        }
        
        //Get objects
        JsonObject object = reader.readObject();
        
        //Close reader
        reader.close();
        
        //Get strings
        String botName = object.getString("bot_name");
        String author = "Author: georgyorgy1 / Darth Squidward";
        String build = "Build: " + object.getString("build");
        String jdkVersion = "JDK Version: " + System.getProperty("java.version");
        String memory = "Memory: " + Long.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " / " + Long.toString(Runtime.getRuntime().totalMemory() / 1048576) + " MB";
        String uptime = "Uptime: " + getUptime();
        String shard = "Shard: " + event.getJDA().getShardInfo();
        String sourceCode = "Source Code: " + "https://github.com/georgyorgy1/shinobubot_java";
        String message = botName + "\n" + "\n" + author + "\n" + build + "\n" + jdkVersion + "\n" + memory + "\n" + uptime + "\n" + shard + "\n" + sourceCode;
        
        //Reply
        event.reply(message);
    }
}
