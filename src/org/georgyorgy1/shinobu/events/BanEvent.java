package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.JDA;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.InfractionChannel;

public class BanEvent extends ListenerAdapter
{
    @Override
    public void onGuildBan(GuildBanEvent event)
    {
        Infraction infraction = new Infraction();
        Guild guild = event.getGuild();
        InfractionChannel channel = new InfractionChannel();
        String privateChannelId = channel.getPrivateChannel(guild);
        JDA jda = event.getJDA();
        MessageChannel privateChannel = jda.getTextChannelById(privateChannelId);
        String publicChannelId = channel.getPublicChannel(guild);
        MessageChannel publicChannel = jda.getTextChannelById(publicChannelId);
        
        privateChannel.sendMessage(infraction.getPrivateLogMessage(event)).queue();
        publicChannel.sendMessage(infraction.getPublicLogMessage(event)).queue();
    }
}
