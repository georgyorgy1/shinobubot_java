package org.georgyorgy1.shinobu.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PingCommand extends Command
{
    public PingCommand()
    {
        this.name = "ping";
        this.help = "pong!";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        ce.reply("Pong!" + "\n" + "Latency: " + ce.getJDA().getPing() + "ms");
    }
}
