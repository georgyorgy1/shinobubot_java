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

public class SetInfractionsChannel extends Command
{
    private DatabaseManager manager;
    private Logger logger;
    
    public SetInfractionsChannel()
    {
        this.name = "setmodlogchannels";
        this.aliases = new String[] {"setlogchannels", "setloggingchannels"};
        this.manager = new DatabaseManager();
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            String[] args = event.getArgs().split("\\s+");
            String[] channels = {args[0].replaceAll("[<#>]", ""), args[1].replaceAll("[<#>]", "")}; //0 private, 1 public
            Connection connection = manager.openConnection();
            String statement = "INSERT INTO infractions_channels VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            
            if (channels[0].isEmpty() || channels[1].isEmpty())
            {
                event.reply("Invalid channel ID(s)!");
            }
            
            else
            {
                String guild = event.getGuild().getId();
                
                try
                {
                    preparedStatement.setString(1, guild);
                    preparedStatement.setString(2, channels[0]);
                    preparedStatement.setString(3, channels[1]);
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
                
                event.reply("Channels added!");
            }
        }
        
        else
        {
            event.reply("You do not have the Manage Server permission!");
        }
    }
}