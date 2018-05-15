package org.georgyorgy1.shinobu.commands.moderation;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.List;

import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class BanCommand extends Command
{
    public BanCommand()
    {
        this.name = "ban";
    }
    
    @Override
    protected void execute(CommandEvent ce)
    {
        if (ce.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            String[] args = ce.getArgs().split("\\s+");
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

            ce.getGuild().getController().ban(ce.getGuild().getMemberById(user), 1, reason + " | " + " Moderator: " + ce.getAuthor().getName() + "#" + ce.getAuthor().getDiscriminator()).queue();
            ce.reply("<@!" + user + "> was banned from the server with the following reason: " + reason);
        }
        
        else
        {
            ce.reply("You do not have the Ban Members permission!");
        }
    } 
}
