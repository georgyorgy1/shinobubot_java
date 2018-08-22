package org.georgyorgy1.shinobu.commands.moderator;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.Channel;

public class AddReasonCommand extends Command
{
    public AddReasonCommand()
    {
        this.name = "reason";
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        Infraction infraction = null;
        Channel channel = null;
        int caseNumber = -1;
        String log = "";
        
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES))
        {
            infraction = new Infraction();            
            channel = new Channel(event.getGuild(), event.getJDA());
            caseNumber = infraction.getLatestCaseNumber(event.getGuild());
            log = infraction.getPublicLogMessage(caseNumber, event.getArgs());
            infraction.setMessageId(channel.getChannel(1).getLatestMessageId(), caseNumber);
            channel.editMessage(channel.getChannel(1), infraction.getMessageId(caseNumber), log); //channel, messageId(caseNumber), reason
            event.reply("Log message sent!");
        }
        
        else
        {
            event.reply("You are not allowed to modify moderation logs!");
        }
    }
}