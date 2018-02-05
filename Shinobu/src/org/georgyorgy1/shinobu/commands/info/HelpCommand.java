package org.georgyorgy1.shinobu.commands.info;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class HelpCommand extends Command
{
    public HelpCommand()
    {
        this.name = "commands";
        this.aliases = new String[]{"cmds", "cmd", "c"};
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        String helpInfo = null;

        try
        {
            helpInfo = new String(Files.readAllBytes(Paths.get("files/help.txt")));
            ce.reply(helpInfo);
        }

        catch (IOException exception)
        {
            Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.info.HelpCommand");
            logger.error(exception.toString());
        }
    }
}
