package org.georgyorgy1.shinobu.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestartCommand extends Command
{
    private Logger logger;
    
    public RestartCommand()
    {
        this.name = "restart";
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() == true)
        {
            try
            {
                Runtime.getRuntime().exec("java -jar Shinobu.jar");
                logger.info("Shinobu is restrating, please wait...");
                event.reply("Shinobu is restarting, please wait...");
                event.getJDA().shutdownNow();
            } 
            
            catch (IOException exception)
            {
                logger.error(exception.toString());
            }
        }
    }
}