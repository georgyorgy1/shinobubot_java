package org.georgyorgy1.shinobu.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeGameCommand extends Command
{
    private Logger logger;
    
    public ChangeGameCommand()
    {
        this.name = "changegame";
        this.help = "Changes the bot's presence message. Usage is !changegame <args>";
        this.logger = LoggerFactory.getLogger(ChangeGameCommand.class.getName());
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() == true)
        {
            logger.info("Game changed to: " + event.getArgs());
            event.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, event.getArgs()));
            event.reply("Successfully changed game name to: " + event.getArgs());
        }
    }
}