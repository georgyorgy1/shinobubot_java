package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.JDA;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.InfractionChannel;;

public class RemoveRoleEvent extends ListenerAdapter
{
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event)
    {
        Infraction infraction = new Infraction();
        Guild guild = event.getGuild();
        Role role = event.getRoles().get(0);
        
        if (infraction.isRoleLoggable(guild, role))
        {
            InfractionChannel channel = new InfractionChannel();
            JDA jda = event.getJDA();
            String publicChannelId = channel.getPublicChannel(guild);
            MessageChannel publicChannel = jda.getTextChannelById(publicChannelId);
            User user = event.getUser();
            String message = infraction.getRemovalMessage(guild, user, role);
            
            publicChannel.sendMessage(message).queue();
        }
    }
}