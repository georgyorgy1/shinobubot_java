package org.georgyorgy1.shinobu.events.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.core.entities.Guild;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;
import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;

public class InfractionChannel
{
    private Logger logger;
    private DatabaseManager manager;
    
    public InfractionChannel()
    {
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
        this.manager = new DatabaseManager();
    }
    
    public String getPublicChannel(Guild guild)
    {
        Connection connection = manager.openConnection();
        String statement = "SELECT public_channel FROM infractions_channels WHERE guild = ?";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);

        try
        {
            preparedStatement.setString(1, guild.getId());
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        String channel = "";
        
        try
        {
            while (resultSet.next())
            {
                channel = resultSet.getString("public_channel");
            }
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        finally
        {
            manager.closeResultSet(resultSet);
            manager.closePreparedStatement(preparedStatement);
            manager.closeConnection(connection);
        }
        
        return channel;
    }
    
    public String getPrivateChannel(Guild guild)
    {
        Connection connection = manager.openConnection();
        String statement = "SELECT private_channel FROM infractions_channels WHERE guild = ?";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);

        try
        {
            preparedStatement.setString(1, guild.getId());
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        String channel = "";
        
        try
        {
            while (resultSet.next())
            {
                channel = resultSet.getString("private_channel");
            }
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        finally
        {
            manager.closeResultSet(resultSet);
            manager.closePreparedStatement(preparedStatement);
            manager.closeConnection(connection);
        }
        
        return channel;
    }
}
