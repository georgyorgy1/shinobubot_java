package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.Channel;

public class BanEvent extends ListenerAdapter
{
    @Override
    public void onGuildBan(GuildBanEvent event)
    {
        //Get channel and infraction
        Infraction infraction = new Infraction();
        Channel channel = new Channel(event.getGuild(), event.getJDA());
        
        //Send Message
        channel.sendMessage(channel.getChannel(0), infraction.getPrivateLogMessage(event));
        channel.sendMessage(channel.getChannel(1), infraction.getPublicLogMessage(event));
    }
}