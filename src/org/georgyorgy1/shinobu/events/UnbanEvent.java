package org.georgyorgy1.shinobu.events;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.JDA;

import org.georgyorgy1.shinobu.events.tables.Infraction;
import org.georgyorgy1.shinobu.events.tables.InfractionChannel;;

public class UnbanEvent extends ListenerAdapter
{
    @Override
    public void onGuildUnban(GuildUnbanEvent event)
    {
        InfractionChannel channel = new InfractionChannel();
        Guild guild = event.getGuild();
        String publicChannelId = channel.getPublicChannel(guild);
        JDA jda = event.getJDA();
        MessageChannel publicChannel = jda.getTextChannelById(publicChannelId);
        User user = event.getUser();
        Infraction infraction = new Infraction();
        String message = infraction.getUnbanMessage(guild, user);
        
        publicChannel.sendMessage(message).queue();
    }
}
