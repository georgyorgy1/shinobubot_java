package org.georgyorgy1.shinobu.commands.general;

import java.awt.Color;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;
import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;

public class CustomCommand extends ListenerAdapter
{
    private Logger logger;
    private DatabaseManager manager;
    
    public CustomCommand()
    {
        this.logger = LoggerFactory.getLogger(CustomCommand.class.getName());
        this.manager = new DatabaseManager();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot() == false)
        {
            Connection connection = manager.openConnection();
            String statement = "SELECT response FROM custom_commands WHERE guild = ? AND command_name = ? ORDER BY RANDOM() LIMIT 1";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            Message message = event.getMessage();
            
            try
            {
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setString(2, message.getContentDisplay());
            }

            catch (SQLException exception)
            {
                logger.error(exception.toString());
            }
            
            ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
            String response = "";
            
            try
            {
                while (resultSet.next())
                {
                    response = resultSet.getString("response");
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

            if (!response.equals(""))
            {
                MessageChannel channel = event.getChannel();
                JsonReader reader = null;
                JsonObject object = null;
                boolean validJson = true;
                
                try
                {
                    reader = Json.createReader(new StringReader(response));
                    object = reader.readObject();
                }
                
                catch (JsonException exception)
                {
                    logger.info("Not a JSON string. Moving on...");
                    logger.warn(exception.toString());
                    validJson = false;
                }
                
                if (validJson)
                {
                    String hexCode = Integer.toHexString(object.getInt("color")).toUpperCase();
                    String url = object.getString("image");
                    Color color = new Color(Integer.parseInt(hexCode, 16));
                    EmbedBuilder embed = new EmbedBuilder();
                    
                    embed.setColor(color);
                    embed.setImage(url);
                    channel.sendMessage(embed.build()).queue();
                }
                
                else
                {
                    channel.sendMessage(response).queue();
                }
            }
        }
    }
}