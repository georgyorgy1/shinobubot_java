package org.georgyorgy1.shinobu.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownCommand extends Command
{
    private Logger logger;
    
    public ShutdownCommand()
    {
        this.name = "kill";
        this.help = "kills the bot";
        this.aliases = new String[]{"shutdown", "terminate", "die"};
        this.logger = LoggerFactory.getLogger(ShutdownCommand.class.getName());
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() == true)
        {
            event.reply("Shinobu is now shutting down. Please wait...");
            logger.info("Bot was called to shutdown.");
            event.getJDA().shutdown();
        }
    }
}
