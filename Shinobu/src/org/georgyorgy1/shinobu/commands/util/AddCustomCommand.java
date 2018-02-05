package org.georgyorgy1.shinobu.commands.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.Permission;

public class AddCustomCommand extends Command
{
    public AddCustomCommand()
    {
        this.name = "acr";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        if (ce.isOwner() || ce.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.util.AddCustomCommand");
            String[] args = ce.getArgs().split("\\s+");
            List<String> temp = new ArrayList<>();
            int i = 0;

            while (!args[i].equals("/"))
            {
                temp.add(args[i]);
                i++;
            }

            String commandName = String.join(" ", temp);

            try
            {
                Files.write(Paths.get("files/customcommands.txt"), (commandName + "\n").getBytes(), StandardOpenOption.APPEND);
            }

            catch (IOException exception)
            {
                logger.error(exception.toString());
            }

            String response = "";

            for (int j = Arrays.asList(args).indexOf("/") + 1; j < args.length; j++)
            {
                response = response + args[j] + " ";
            }

            try
            {
                File file = new File("files/custom commands/" + commandName + ".txt");
                file.createNewFile();
                Files.write(Paths.get("files/custom commands/" + commandName + ".txt"), response.getBytes(), StandardOpenOption.APPEND);
            }

            catch (IOException exception)
            {
                logger.error(exception.toString());
            }

            logger.info("A new command was added!"); //remove when all commands are isolated by guild
            ce.reply("Command added!");
        }
    }
}
