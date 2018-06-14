package org.georgyorgy1.shinobu.commands.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.Permission;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class AddCustomCommand extends Command
{
    public AddCustomCommand()
    {
        this.name = "acr";
        this.help = "Add a custom command. For owners and admins only. Usage is !acr <command name> | <response>";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.isOwner() || event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            Logger logger = LoggerFactory.getLogger("org.georgyorgy1.shinobu.commands.util.AddCustomCommand");
            String[] args = event.getArgs().split("\\s+");
            List<String> temp = new ArrayList<>();
            boolean illegalCharacterFound = false;

            //Credits to Lickorice
            for (int g = 0; g < args.length; g++)
            {
                if (g == 1)
                {
                    if (args[g - 1].equals("|"))
                    {
                        illegalCharacterFound = true;
                    }
                }
            }
            
            if (illegalCharacterFound == false)
            {
                int i = 0;

                while (!args[i].equals("|"))
                {
                    temp.add(args[i]);
                    i++;
                }

                String commandName = String.join(" ", temp);
                String response = "";

                for (int j = Arrays.asList(args).indexOf("|") + 1; j < args.length; j++)
                {
                    response = response + args[j] + " ";
                }

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
                String sql = "INSERT INTO custom_commands VALUES (?, ?, ?)";

                try
                {
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, event.getGuild().getId());
                    preparedStatement.setString(2, commandName);
                    preparedStatement.setString(3, response);
                    preparedStatement.executeUpdate();
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

                event.reply("Command added!");
            }
            
            else
            {
                event.reply("Illegal character found. Command was not added.");
            }
        }
    }
}
