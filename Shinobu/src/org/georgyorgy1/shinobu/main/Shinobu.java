/*
Copyright (c) 2018 georgyorgy1

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.georgyorgy1.shinobu.main;

import java.io.FileReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.JDABuilder;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import org.georgyorgy1.shinobu.commands.fun.FlipCommand;
import org.georgyorgy1.shinobu.commands.fun.PalindromeCommand;
import org.georgyorgy1.shinobu.commands.fun.PingCommand;
import org.georgyorgy1.shinobu.commands.info.AboutCommand;
import org.georgyorgy1.shinobu.commands.info.HelpCommand;
import org.georgyorgy1.shinobu.commands.moderation.BanCommand;
import org.georgyorgy1.shinobu.commands.moderation.KickCommand;
import org.georgyorgy1.shinobu.commands.moderation.RemoveRoleCommand;
import org.georgyorgy1.shinobu.commands.moderation.SetRoleCommand;
import org.georgyorgy1.shinobu.commands.owner.ChangeGameCommand;
import org.georgyorgy1.shinobu.commands.owner.MindtrickCommand;
import org.georgyorgy1.shinobu.commands.owner.ShutdownCommand;
import org.georgyorgy1.shinobu.commands.shitpost.CheckEmCommand;
import org.georgyorgy1.shinobu.commands.shitpost.CurrentYearCommand;
import org.georgyorgy1.shinobu.commands.util.AddCustomCommand;
import org.georgyorgy1.shinobu.commands.util.CustomCommand;
import org.georgyorgy1.shinobu.commands.util.ListCustomCommand;
import org.georgyorgy1.shinobu.commands.util.RemoveCustomCommand;

public class Shinobu
{
    public static void main(String[] args)
    {
        final Logger logger = LoggerFactory.getLogger(Shinobu.class.getName());
        JsonReader reader = null;
        
        //Get information from JSON file
        try
        {
            reader = Json.createReader(new FileReader("files/config.json"));
        }
        
        catch (IOException exception)
        {
            logger.error(exception.toString());
        }
        
        //Get objects
        JsonObject object = reader.readObject();
        
        //Close Reader
        reader.close();
        
        //Create a new bot client
        CommandClientBuilder client = new CommandClientBuilder();

        //Setup bot presence
        client.setGame(Game.playing("!cmds for commands"));
        client.setOwnerId(object.getString("owner_id"));
        client.setPrefix(object.getString("prefix"));
        
        //Add commands
        client.addCommand(new ShutdownCommand());
        client.addCommand(new MindtrickCommand());
        client.addCommand(new FlipCommand());
        client.addCommand(new PalindromeCommand());
        client.addCommand(new ChangeGameCommand());
        client.addCommand(new PingCommand());
        client.addCommand(new AddCustomCommand());
        client.addCommand(new HelpCommand());
        client.addCommand(new AboutCommand());
        client.addCommand(new CheckEmCommand());
        client.addCommand(new CurrentYearCommand());
        client.addCommand(new ListCustomCommand());
        client.addCommand(new SetRoleCommand());
        client.addCommand(new RemoveRoleCommand());
        client.addCommand(new RemoveCustomCommand());
        client.addCommand(new BanCommand());
        client.addCommand(new KickCommand());
        
        //no defaults
        client.useHelpBuilder(false);

        //Prepare bot
        JDABuilder shard = new JDABuilder(AccountType.BOT).setToken(object.getString("token"));
        
        //Add commands and events
        shard.addEventListener(new EventWaiter());
        shard.addEventListener(new CustomCommand());
        shard.addEventListener(client.build());
        
        //Sharding
        int shards = object.getInt("shards");

        for (int i = 0; i < shards; i++)
        {
            try
            {
                shard.useSharding(i, shards).buildAsync();
            }
            
            catch (LoginException exception)
            {
                logger.error(exception.toString());
            }
        }
    }
}
