package org.georgyorgy1.shinobu.commands.moderation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class KickCommand extends Command
{
    public KickCommand()
    {
        this.name = "kick";
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS))
        {
            String[] args = event.getArgs().split("\\s+");
            String user = args[0].replace("<", "").replace("@", "").replace("!", "").replace(">", "");
            List<String> combinedStrings = new ArrayList<String>();

            for (int i = 1; i < args.length; i++)
            {
                combinedStrings.add(args[i]);
            }
            
            String reason = String.join(" ", combinedStrings);
            
            if (reason.equals(""))
            {
                reason = "No reason was provided.";
            }
            
            Logger logger = LoggerFactory.getLogger(KickCommand.class.getName());
            
            try
            {
                event.getGuild().getController().kick(event.getGuild().getMemberById(user), reason + " | " + " Moderator: " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator()).queue();
                event.reply("<@!" + user + "> was kicked from the server with the following reason: " + reason);
            }
            
            catch (HierarchyException exception)
            {
                logger.error(exception.toString());
                event.reply("I cannot kick that user due to an issue with the role hierarchy.");
            }
            
            catch (InsufficientPermissionException exception)
            {
                logger.error(exception.toString());
                event.reply("I do not have the permission to kick that user.");
            }
        }
        
        else
        {
            event.reply("You do not have the Ban Members permission!");
        }
    }
}
