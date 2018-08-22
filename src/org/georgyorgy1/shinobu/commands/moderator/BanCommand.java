package org.georgyorgy1.shinobu.commands.moderator;

import java.util.ArrayList;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BanCommand extends Command
{
    private Logger logger;
    
    public BanCommand()
    {
        this.name = "ban";
        this.logger = LoggerFactory.getLogger(BanCommand.class.getName());
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.BAN_MEMBERS))
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
            
            Guild guild = event.getGuild();
            GuildController controller = guild.getController();
            Member member = guild.getMemberById(user);
            User author = event.getAuthor();
            String logReason = reason + " | " + " Moderator: " + author.getName() + "#" + author.getDiscriminator();
            
            try
            {
                controller.ban(member, 1, logReason).queue();
                event.reply("<@!" + user + "> was banned from the server with the following reason: " + reason);
            }
            
            catch (HierarchyException exception)
            {
                logger.error(exception.toString());
                event.reply("I cannot ban that user due to an issue with the role hierarchy.");
            }
            
            catch (InsufficientPermissionException exception)
            {
                logger.error(exception.toString());
                event.reply("I do not have the permission to ban that user.");
            }
        }
        
        else
        {
            event.reply("You do not have the Ban Members permission!");
        }
    } 
}
