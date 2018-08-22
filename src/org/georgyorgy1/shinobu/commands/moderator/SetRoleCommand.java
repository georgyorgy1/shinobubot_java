package org.georgyorgy1.shinobu.commands.moderator;

import java.util.ArrayList;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetRoleCommand extends Command
{
    private Logger logger;
    
    public SetRoleCommand()
    {
        this.name = "setrole";
        this.help = "Gives a role to a user.";
        this.logger = LoggerFactory.getLogger(SetRoleCommand.class.getName());
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.MANAGE_ROLES))
        {
            String[] args = event.getArgs().split("\\s+");
            String user = args[0].replace("<", "").replace("@", "").replace("!", "").replace(">", "");
            List<String> combinedStrings = new ArrayList<String>();

            for (int i = 1; i < args.length; i++)
            {
                combinedStrings.add(args[i]);
            }
            
            String roleName = String.join(" ", combinedStrings);
            Guild guild = event.getEvent().getGuild();
            GuildController controller = guild.getController();
            Member member = guild.getMemberById(user);
            List<Role> roles = guild.getRolesByName(roleName, true);

            try
            {
                controller.addSingleRoleToMember(member, roles.get(0)).queue();
                event.reply("<@!" + user + "> was given the role named " + roleName);
            }
            
            catch (HierarchyException exception)
            {
                logger.error(exception.toString());
                event.reply("I cannot add that role due to an issue with the role hierarchy.");
            }
            
            catch (InsufficientPermissionException exception)
            {
                logger.error(exception.toString());
                event.reply("I do not have the permission to add a role.");
            }
            
            catch (IndexOutOfBoundsException exception)
            {
                logger.error(exception.toString());
                event.reply("The role you are trying to assign does not exist!");
            }
        }
        
        else
        {
            event.reply("You do not have the Manage Roles permission!");
        }
    }
}