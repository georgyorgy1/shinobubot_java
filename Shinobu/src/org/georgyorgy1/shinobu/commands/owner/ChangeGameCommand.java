package org.georgyorgy1.shinobu.commands.owner;

import net.dv8tion.jda.core.entities.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ChangeGameCommand extends Command
{
    public ChangeGameCommand()
    {
        this.name = "changegame";
        this.help = "Changes the bot's presence message. Usage is !changegame <args>";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() == true)
        {
            final Logger logger = LoggerFactory.getLogger(ChangeGameCommand.class.getName());
            
            logger.info("Game changed to: " + event.getArgs());
            event.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, event.getArgs()));
            event.reply("Successfully changed game name to: " + event.getArgs());
        }
    }
}
