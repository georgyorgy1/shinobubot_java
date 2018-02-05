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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import org.georgyorgy1.shinobu.commands.fun.FlipCommand;
import org.georgyorgy1.shinobu.commands.fun.PalindromeCommand;
import org.georgyorgy1.shinobu.commands.fun.PingCommand;
import org.georgyorgy1.shinobu.commands.info.AboutCommand;
import org.georgyorgy1.shinobu.commands.info.HelpCommand;
import org.georgyorgy1.shinobu.commands.owner.ChangeGameCommand;
import org.georgyorgy1.shinobu.commands.owner.MindtrickCommand;
import org.georgyorgy1.shinobu.commands.owner.ShutdownCommand;
import org.georgyorgy1.shinobu.commands.shitpost.CheckEmCommand;
import org.georgyorgy1.shinobu.commands.shitpost.CurrentYearCommand;
import org.georgyorgy1.shinobu.commands.util.AddCustomCommand;
import org.georgyorgy1.shinobu.commands.util.CustomCommand;

public class Shinobu extends ListenerAdapter
{
    public static void main(String[] args) throws IOException, LoginException
    {
        List<String> config = Files.readAllLines(Paths.get("files/config.txt"));
        CommandClientBuilder client = new CommandClientBuilder();

        client.setGame(Game.playing("!cmds for commands"));
        client.setOwnerId(config.get(1));
        client.setPrefix(config.get(2));
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

        JDABuilder shard = new JDABuilder(AccountType.BOT).setToken(config.get(0)).addEventListener(new EventWaiter()).addEventListener(client.build()).addEventListener(new CustomCommand());
        int maxShard = 3;

        for (int i = 0; i < maxShard; i++)
        {
            shard.useSharding(i, maxShard).buildAsync();
        }
    }
}
