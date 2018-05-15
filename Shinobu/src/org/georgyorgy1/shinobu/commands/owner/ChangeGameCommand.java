package org.georgyorgy1.shinobu.commands.owner;

import net.dv8tion.jda.core.entities.Game;

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
    protected void execute(CommandEvent ce)
    {
        if (ce.isOwner() == true)
        {
            ce.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, ce.getArgs()));
            ce.reply("Successfully changed game name to: " + ce.getArgs());
        }
    }
}
