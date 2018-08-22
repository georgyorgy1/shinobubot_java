package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.Channel;

public class SetRoleEvent extends ListenerAdapter
{
    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event)
    {
        //Get infraction
        Infraction infraction = new Infraction();
        
        //Check permission
        if (infraction.isRoleLoggable(event.getGuild(), event.getRoles().get(0)))
        {
            //get channels
            Channel channel = new Channel(event.getGuild(), event.getJDA());
            
            //Send message
            channel.sendMessage(channel.getChannel(0), infraction.getPrivateLogMessage(event));
            channel.sendMessage(channel.getChannel(1), infraction.getPublicLogMessage(event));
        }
    }
}