package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.Channel;;

public class UnbanEvent extends ListenerAdapter
{
    @Override
    public void onGuildUnban(GuildUnbanEvent event)
    {
        //Get infraction and channels
        Infraction infraction = new Infraction();
        Channel channel = new Channel(event.getGuild(), event.getJDA());
        
        //Send message
        channel.sendMessage(channel.getChannel(1), infraction.getUnbanMessage(event.getGuild(), event.getUser()));
    }
}