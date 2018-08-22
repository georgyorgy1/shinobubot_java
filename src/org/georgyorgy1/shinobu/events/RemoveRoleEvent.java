package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.Channel;;

public class RemoveRoleEvent extends ListenerAdapter
{
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event)
    {
        //Get infraction
        Infraction infraction = new Infraction();
        
        //Check if role is logged
        if (infraction.isRoleLoggable(event.getGuild(), event.getRoles().get(0)))
        {
            //Get channels
            Channel channel = new Channel(event.getGuild(), event.getJDA());
            
            //Send message
            channel.sendMessage(channel.getChannel(1), infraction.getRemovalMessage(event.getGuild(), event.getUser(), event.getRoles().get(0))); //send_message looks better xd
        }
    }
}