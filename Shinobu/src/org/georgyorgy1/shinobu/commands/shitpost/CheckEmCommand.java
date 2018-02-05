package org.georgyorgy1.shinobu.commands.shitpost;

import java.util.Random;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CheckEmCommand extends Command
{
    public CheckEmCommand()
    {
        this.name = "checkem";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        int postNumber = new Random().nextInt((999999999 - 100000000) + 1) - 100000000;
        ce.reply("Check em!" + "\n" + "Your number: " + Integer.toString(postNumber));
    }
}
