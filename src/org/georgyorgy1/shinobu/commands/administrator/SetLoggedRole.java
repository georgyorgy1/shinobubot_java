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

public class SetLoggedRole extends Command
{
    public DatabaseManager manager;
    public Logger logger;
    
    public SetLoggedRole()
    {
        this.name = "setloggedrole";
        this.manager = new DatabaseManager();
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            Connection connection = manager.openConnection();
            String statement = "INSERT INTO listed_roles VALUES (?, ?)";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            
            if (!event.getArgs().isEmpty())
            {
                try
                {
                    preparedStatement.setString(1, event.getGuild().getId());
                    preparedStatement.setString(2, event.getArgs());
                    preparedStatement.executeUpdate();
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
                
                event.reply("Role added!");
            }
        }
        
        else
        {
            event.reply("You do not have the Administrator permission!");
        }
    }
}