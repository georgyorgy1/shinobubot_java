package org.georgyorgy1.shinobu.commands.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class ListCustomCommand extends Command
{
    public ListCustomCommand()
    {
        this.name = "lcr";
        this.help = "List custom commands. Pages start at 0. For owners and admins only.";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() || event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            final Logger logger = LoggerFactory.getLogger(ListCustomCommand.class.getName());
            Connection connection = null;
            String url = "jdbc:sqlite:files/shinobu.db";
            String[] args = event.getArgs().split("\\s+");

            try
            {
                connection = DriverManager.getConnection(url);
            }

            catch (SQLException exception)
            {
                logger.error(exception.toString());
            }

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String sql = "SELECT rowid, command_name, response FROM custom_commands WHERE guild = ? LIMIT 10 OFFSET ?";
            List<String> rowIds = new ArrayList<>();
            List<String> commandNames = new ArrayList<>();
            List<String> responses = new ArrayList<>();
            int pageNumber = 0;
            
            if (!args[0].isEmpty())
            {
                try
                {
                    pageNumber = Integer.parseInt(args[0]) * 10;
                }

                catch (NumberFormatException exception)
                {
                    logger.warn(exception.toString());
                    event.reply("That is not a valid page number.");
                    return;
                }
            }

            try
            {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setInt(2, pageNumber);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    rowIds.add(Integer.toString(resultSet.getInt("rowid")));
                    commandNames.add(resultSet.getString("command_name"));
                    responses.add(resultSet.getString("response"));
                }
            }

            catch (SQLException exception)
            {
                logger.error(exception.toString(), exception);
            }
            
            finally
            {
                try
                {
                    resultSet.close();
                }
                
                catch (SQLException exception)
                {
                    logger.error(exception.toString());
                }
                
                finally
                {
                    try
                    {
                        preparedStatement.close();
                    }

                    catch (SQLException exception)
                    {
                        logger.error(exception.toString());
                    }

                    finally
                    {
                        try
                        {
                            connection.close();
                        }

                        catch (SQLException exception)
                        {
                            logger.error(exception.toString());
                        }
                    }
                }
            }

            String response = "";
            
            for (int i = 0; i < rowIds.size(); i++) 
            {
                response = response + "ID: " + rowIds.get(i) + "\t" + "Command Name: " + commandNames.get(i) + "\t" + "Response: `" + responses.get(i) + "`" + "\n";
            }

            if (response.equals(""))
            {
                event.reply("No custom commands found. Either you have no commands in this guild or you have entered an invalid page number.");
            }
            
            else
            {
                event.reply(response);
            }
        }
    }
}
