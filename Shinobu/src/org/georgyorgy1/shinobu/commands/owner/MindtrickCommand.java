package org.georgyorgy1.shinobu.commands.owner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MindtrickCommand extends Command
{
    public MindtrickCommand()
    {
        this.name = "mindtrick";
        this.help = "This is not the command you are looking for. Usage is !mindtrick <channel ID> <args>";
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() == true)
        {
            String[] args = event.getArgs().split("\\s+");
            String message = "";

            for (int i = 1; i < args.length; i++)
            {
                message = message + args[i] + " ";
            }
            
            final Logger logger = LoggerFactory.getLogger(MindtrickCommand.class.getName());

            logger.info("Mindtrick command used at channel (" + args[0] + ") with message (" + message + ")");
            event.getJDA().getTextChannelById(Long.parseLong(args[0])).sendMessage(message).queue();
            event.reply("Message sent!");
        }
    }
}
