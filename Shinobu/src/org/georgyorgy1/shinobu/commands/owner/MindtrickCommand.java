package org.georgyorgy1.shinobu.commands.owner;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MindtrickCommand extends Command
{
    public MindtrickCommand()
    {
        this.name = "mindtrick";
        this.help = "This is not the command you are looking for. Usage is !mindtrick <channel ID> <args>";
    }
    
    @Override
    protected void execute(CommandEvent ce)
    {
        String[] args = ce.getArgs().split("\\s+");
        String message = "";
        
        for (int i = 1; i < args.length; i++)
        {
            message = message + args[i] + " ";
        }
        
        ce.getJDA().getTextChannelById(Long.parseLong(args[0])).sendMessage(message).queue();
        ce.reply("Message sent!");
    }
}
