package org.georgyorgy1.shinobu.events.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.JDA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;
import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;

public class Channel
{
    private Logger logger;
    private DatabaseManager manager;
    private Guild guild;
    private JDA jda;
    
    public Channel(Guild guild, JDA jda)
    {
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
        this.manager = new DatabaseManager();
        this.guild = guild;
        this.jda = jda;
    }
    
    public void editMessage(MessageChannel channel, String messageId, String message)
    {
        channel.editMessageById(messageId, message).queue();
    }
    
    public void sendMessage(MessageChannel channel, String message)
    {
        channel.sendMessage(message).queue();
    }
    
    public MessageChannel getChannel(int type)
    {
        Connection connection = manager.openConnection();
        String statement = "";
        
        if (type == 0)
        {
            statement = "SELECT private_channel FROM infractions_channels WHERE guild = ?";
        }
        
        else
        {
            statement = "SELECT public_channel FROM infractions_channels WHERE guild = ?";
        }
        
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
                if (type == 0)
                {
                    channel = resultSet.getString("private_channel");
                }
                
                else
                {
                    channel = resultSet.getString("public_channel");
                }
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
        
        return jda.getTextChannelById(channel);
    }
}