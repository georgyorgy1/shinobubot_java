package org.georgyorgy1.shinobu.commands.moderator;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.InfractionChannel;

public class AddReasonCommand extends Command
{
    public AddReasonCommand()
    {
        this.name = "reason";
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES))
        {
            Infraction infraction = new Infraction();
            Guild guild = event.getGuild();
            int caseNumber = infraction.getLatestCaseNumber(guild);
            String reason = event.getArgs();
            String message = infraction.getPublicLogMessage(caseNumber, reason);
            InfractionChannel channel = new InfractionChannel();
            String channelId = channel.getPublicChannel(guild);
            JDA jda = event.getJDA();
            MessageChannel publicChannel = jda.getTextChannelById(channelId);
            String messageId = publicChannel.getLatestMessageId();
            
            infraction.setMessageId(messageId, caseNumber);
            publicChannel.editMessageById(infraction.getMessageId(caseNumber), message).queue();
            event.reply("Reason sent!");
        }
    }
}