package org.georgyorgy1.shinobu.commands.info;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class InviteCommand extends Command
{
    @Override
    protected void execute(CommandEvent event)
    {
        event.reply("Invite link to bot: https://discordapp.com/oauth2/authorize?client_id=382491524656529418&scope=bot&permissions=1073081543");
    }
}