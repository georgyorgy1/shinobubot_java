package org.georgyorgy1.shinobu.commands.info;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class AboutCommand extends Command
{
    public AboutCommand()
    {
        this.name = "about";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        try
        {
            String botName = Files.readAllLines(Paths.get("files/config.txt")).get(4);
            String author = "Author: georgyorgy1 / Darth Squidward";
            String build = "Build: " + Files.readAllLines(Paths.get("files/config.txt")).get(3);
            String jdkVersion = "JDK Version: " + System.getProperty("java.version");
            String memory = "Memory: " + Long.toString((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " / " + Long.toString(Runtime.getRuntime().totalMemory() / 1048576) + " MB";
            String shard = "Shard: " + ce.getJDA().getShardInfo();

            String message = botName + "\n" + "\n" + author + "\n" + build + "\n" + jdkVersion + "\n" + memory + "\n" + shard;
            ce.reply(message);
        }

        catch (IOException exception)
        {
            Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.main.Shinobu");
            logger.error(exception.toString());
        }
    }
}
