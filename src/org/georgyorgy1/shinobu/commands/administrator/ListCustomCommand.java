package org.georgyorgy1.shinobu.commands.administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;
import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;

public class ListCustomCommand extends Command
{
    private Logger logger;
    private DatabaseManager manager;
    
    public ListCustomCommand()
    {
        this.name = "lcr";
        this.help = "List custom commands. Pages start at 0. For owners and admins only.";
        this.logger = LoggerFactory.getLogger(ListCustomCommand.class.getName());
        this.manager = new DatabaseManager();
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            Connection connection = manager.openConnection();
            String[] args = event.getArgs().split("\\s+");
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
            
            String statement = "SELECT rowid, command_name, response FROM custom_commands WHERE guild = ? LIMIT 10 OFFSET ?";
            PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
            List<String> rowIds = new ArrayList<>();
            List<String> commandNames = new ArrayList<>();
            List<String> responses = new ArrayList<>();
            
            try
            {
                preparedStatement.setString(1, event.getGuild().getId());
                preparedStatement.setInt(2, pageNumber);
            }
            
            catch (SQLException exception)
            {
                logger.error(exception.toString(), exception);
            }
            
            ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
            
            try
            {
                while (resultSet.next())
                {
                    rowIds.add(Integer.toString(resultSet.getInt("rowid")));
                    commandNames.add(resultSet.getString("command_name"));
                    responses.add(resultSet.getString("response"));
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
        
        else
        {
            event.reply("You do not have the Manage Server permission!");
        }
    }
}