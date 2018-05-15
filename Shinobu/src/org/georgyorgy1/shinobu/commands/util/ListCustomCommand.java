package org.georgyorgy1.shinobu.commands.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListCustomCommand extends Command
{
    public ListCustomCommand()
    {
        this.name = "lcr";
        this.help = "List custom commands. For owners and admins only.";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        if (ce.isOwner() || ce.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.util.ListCustomCommand");
            Connection connection = null;
            String url = "jdbc:sqlite:files/shinobu.db";

            try
            {
                connection = DriverManager.getConnection(url);
            }

            catch (SQLException exception)
            {
                logger.error(null, exception);
            }

            PreparedStatement preparedStatement = null;
            String sql = "SELECT rowid, command_name, response FROM custom_commands WHERE guild = ?";
            List<String> rowIds = new ArrayList<>();
            List<String> commandNames = new ArrayList<>();
            List<String> responses = new ArrayList<>();

            try
            {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ce.getGuild().getId());
                
                ResultSet resultSet = preparedStatement.executeQuery();

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

            String response = "";
            
            for (int i = 0; i < rowIds.size(); i++) 
            {
                response = response + "ID: " + rowIds.get(i) + "\t" + "Command Name: " + commandNames.get(i) + "\t" + "Response: `" + responses.get(i) + "`" + "\n";
            }

            if (response.equals(""))
            {
                ce.reply("There are no custom commands for this guild.");
            }
            
            else
            {
                ce.reply(response);
            }
            
            try
            {
                connection.close();
            }
            
            catch (SQLException exception)
            {
                logger.error(exception.toString(), exception);
            }
        }
    }
}
