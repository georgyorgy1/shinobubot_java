package org.georgyorgy1.shinobu.commands.administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;

public class RemoveCustomCommand extends Command
{
    private Logger logger;
    private DatabaseManager manager;
    
    public RemoveCustomCommand()
    {
        this.name = "dcr";
        this.help = "Remove a custom command. For owners and admins only. Usage is !dcr <command id>";
        this.logger = LoggerFactory.getLogger(RemoveCustomCommand.class.getName());
        this.manager = new DatabaseManager();
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR))
        {
            Connection connection = manager.openConnection();
            String statement = "DELETE FROM custom_commands WHERE rowid = ? AND guild = ?";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            String[] args = event.getArgs().split("\\s+");
            int statementValue = 0;
            
            try
            {
                preparedStatement.setString(1, args[0]);
                preparedStatement.setString(2, event.getGuild().getId());
                statementValue = preparedStatement.executeUpdate();
            }
            
            catch (SQLException exception)
            {
                logger.error(exception.toString());
            }
            
            finally
            {
                manager.closePreparedStatement(preparedStatement);
                manager.closeConnection(connection);
            }

            if (statementValue > 0)
            {
                event.reply("Command removed!");
            }
            
            else
            {
                event.reply("The command you are trying to remove does not exist.");
            }
        }
        
        else
        {
            event.reply("You do not have the Administrator permission!");
        }
    }
}