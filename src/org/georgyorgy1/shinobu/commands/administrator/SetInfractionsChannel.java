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
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR))
        {
            String[] args = event.getArgs().split("\\s+");
            String privateChannel = args[0].replace("<", "").replace("#", "").replace(">", "");
            String publicChannel = args[1].replace("<", "").replace("#", "").replace(">", "");
            Connection connection = manager.openConnection();
            String statement = "INSERT INTO infractions_channels VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            
            if (privateChannel.isEmpty() || publicChannel.isEmpty())
            {
                event.reply("Invalid channel ID(s)!");
            }
            
            else
            {
                String guild = event.getGuild().getId();
                
                try
                {
                    preparedStatement.setString(1, guild);
                    preparedStatement.setString(2, privateChannel);
                    preparedStatement.setString(3, publicChannel);
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
            event.reply("You do not have the Administrator permission!");
        }
    }
}