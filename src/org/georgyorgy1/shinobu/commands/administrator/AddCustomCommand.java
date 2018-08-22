package org.georgyorgy1.shinobu.commands.administrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.core.Permission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;

public class AddCustomCommand extends Command
{
    private Logger logger;
    private DatabaseManager manager; 
    
    public AddCustomCommand()
    {
        this.name = "acr";
        this.help = "Add a custom command. For owners and admins only. Usage is !acr <command name> | <response>";
        this.logger = LoggerFactory.getLogger(AddCustomCommand.class.getName());
        this.manager = new DatabaseManager();
    }

    @Override
    protected void execute(CommandEvent event)
    {
        //Check for illegal character (which is |)
        if (event.getMember().hasPermission(Permission.MANAGE_SERVER))
        {
            boolean valid = true;
            String[] args = event.getArgs().split("\\s+");

            //Credits to Lickorice
            for (int g = 0; g < args.length; g++)
            {
                if (g == 1)
                {
                    if (args[g - 1].equals("|"))
                    {
                        valid = false;
                    }
                }
            }
            
            if (valid)
            {
                int i = 0;
                List<String> temp = new ArrayList<>();

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
                
                Connection connection = manager.openConnection();
                String statement = "INSERT INTO custom_commands VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
                
                try
                {
                    preparedStatement.setString(1, event.getGuild().getId());
                    preparedStatement.setString(2, commandName);
                    preparedStatement.setString(3, response);
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
                
                event.reply("Command added!");
            }
            
            else
            {
                event.reply("Illegal character found. Command was not added.");
            }
        }
        
        else
        {
            event.reply("You do not have the Manage Server permission!");
        }
    }
}
