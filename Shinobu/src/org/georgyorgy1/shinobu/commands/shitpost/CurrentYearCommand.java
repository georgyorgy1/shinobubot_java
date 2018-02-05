package org.georgyorgy1.shinobu.commands.shitpost;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CurrentYearCommand extends Command
{
    public CurrentYearCommand()
    {
        this.name = "currentyear";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        String args = ce.getArgs();
        ce.reply(">" + args + " in the current year.");
    }
}
