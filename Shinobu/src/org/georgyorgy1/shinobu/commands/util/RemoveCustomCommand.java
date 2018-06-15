package org.georgyorgy1.shinobu.commands.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class RemoveCustomCommand extends Command
{
    public RemoveCustomCommand()
    {
        this.name = "dcr";
        this.help = "Remove a custom command. For owners and admins only. Usage is !dcr <command id>";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() || event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            final Logger logger = LoggerFactory.getLogger(RemoveCustomCommand.class.getName());
            String[] args = event.getArgs().split("\\s+");
            String url = "jdbc:sqlite:files/shinobu.db";
            Connection connection = null;
            
            try
            {
                connection = DriverManager.getConnection(url);
            }
            
            catch (SQLException exception)
            {
                logger.error(exception.toString(), exception);
            }
            
            PreparedStatement preparedStatement = null;
            String sql = "DELETE FROM custom_commands WHERE rowid = ? AND guild = ?";
            boolean fromCorrectGuild = false;
            
            try
            {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, args[0]);
                preparedStatement.setString(2, event.getGuild().getId());
                fromCorrectGuild = preparedStatement.execute();
            }
            
            catch (SQLException exception)
            {
                logger.error(exception.toString(), exception);
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
            
            if (fromCorrectGuild == true)
            {
                event.reply("Command removed!");
            }
            
            else
            {
                event.reply("The command you are trying to remove does not exist.");
            }
        }
    }
}