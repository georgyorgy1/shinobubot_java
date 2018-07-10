package org.georgyorgy1.shinobu.commands.info;

import javax.json.JsonObject;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.georgyorgy1.shinobu.managers.JsonManager;

public class HelpCommand extends Command
{
    private JsonObject object;
    
    public HelpCommand()
    {
        this.name = "commands";
        this.help = "This help page";
        this.aliases = new String[]{"cmds", "cmd", "c", "help"};
        this.object = new JsonManager().getJsonObject();
    }

    @Override
    protected void execute(CommandEvent event)
    {
        event.reply(object.getString("help_string"));
    }
}