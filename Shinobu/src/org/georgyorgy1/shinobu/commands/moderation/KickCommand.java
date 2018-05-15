package org.georgyorgy1.shinobu.commands.moderation;

import java.util.ArrayList;
import java.util.List;

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
    protected void execute(CommandEvent ce)
    {
        if (ce.getMember().hasPermission(Permission.KICK_MEMBERS))
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
            
            ce.getGuild().getController().kick(ce.getGuild().getMemberById(user), reason + " | " + " Moderator: " + ce.getAuthor().getName() + "#" + ce.getAuthor().getDiscriminator()).queue();
            ce.reply("<@!" + user + "> was kicked from the server with the following reason: " + reason);
        }
        
        else
        {
            ce.reply("You do not have the Ban Members permission!");
        }
    }
}
