package org.georgyorgy1.shinobu.commands.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCommand extends ListenerAdapter
{
    //Don't ask why. This is the only way I can implement custom commands.
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.util.CustomCommand");
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> customCommands = null;

        try
        {
            customCommands = Files.readAllLines(Paths.get("files/customcommands.txt"));
        }

        catch (IOException exception)
        {
            logger.error(exception.toString());
        }

        if (customCommands.contains(message.getContentDisplay()))
        {
            String response = null;

            try
            {
                response = new String(Files.readAllBytes(Paths.get("files/custom commands/" + message.getContentDisplay() + ".txt")));
            }

            catch (IOException exception)
            {
                logger.error(exception.toString());
            }

            channel.sendMessage(response).queue();
        }
    }
}
