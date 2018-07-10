package org.georgyorgy1.shinobu.commands.info;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.json.JsonObject;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.georgyorgy1.shinobu.managers.JsonManager;

public class AboutCommand extends Command
{
    private JsonObject object;
    
    public AboutCommand()
    {
        this.name = "about";
        this.help = "get information about the bot";
        this.object = new JsonManager().getJsonObject();
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ManagementFactory.getRuntimeMXBean().getUptime());
        long minutes = seconds / 60;
        long hours = seconds / 3600;
        long days = seconds / 86400;
        
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
        
        String botName = object.getString("bot_name");
        String author = "Author: georgyorgy1 / Darth Squidward";
        String build = "Build: " + object.getString("build");
        String jdkVersion = "JDK Version: " + System.getProperty("java.version");
        String memory = "Memory: " + Long.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " / " + Long.toString(Runtime.getRuntime().totalMemory() / 1048576) + " MB";
        String uptime = "Uptime: " + Long.toString(days) + " days, " + Long.toString(hours) + " hours, " + Long.toString(minutes) + " minutes and " + Long.toString(seconds) + " seconds";
        String shard = "Shard: " + event.getJDA().getShardInfo();
        String sourceCode = "Source Code: " + "https://github.com/georgyorgy1/shinobubot_java";
        String message = botName + "\n" + "\n" + author + "\n" + build + "\n" + jdkVersion + "\n" + memory + "\n" + uptime + "\n" + shard + "\n" + sourceCode;
        
        //Reply
        event.reply(message);
    }
}
