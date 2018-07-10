package org.georgyorgy1.shinobu.commands.fun;

import java.util.Random;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class FlipCommand extends Command
{
    public FlipCommand()
    {
        this.name = "flip";
        this.help = "A basic coin flip. Heads or tails.";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        int coin = new Random().nextInt(2);

        if (coin == 0)
        {
            event.reply("You got heads!");
        }

        else
        {
            event.reply("You got tails!");
        }
    }
}
