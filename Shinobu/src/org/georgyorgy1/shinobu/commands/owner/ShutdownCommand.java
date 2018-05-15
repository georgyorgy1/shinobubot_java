package org.georgyorgy1.shinobu.commands.owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ShutdownCommand extends Command
{
    public ShutdownCommand()
    {
        this.name = "kill";
        this.help = "kills the bot";
        this.aliases = new String[]{"shutdown", "terminate", "die"};
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.owner.ShutdownCommand");

        if (ce.isOwner() == true)
        {
            ce.reply("Shinobu is now shutting down. Please wait...");
            logger.info("Bot was called to shutdown.");
            ce.getJDA().shutdown();
        }
    }
}
