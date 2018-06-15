package org.georgyorgy1.shinobu.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PalindromeCommand extends Command
{
    public PalindromeCommand()
    {
        this.name = "palindrome";
        this.help = "checks if the word is a palindrome. Usage is !palindrome <arg>";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String arg = event.getArgs();
        StringBuilder palindrome = new StringBuilder();

        if (palindrome.append(arg).reverse().toString().equals(arg))
        {
            event.reply("It is a palindrome!");
        }

        else
        {
            event.reply("It is not a palindrome!");
        }
    }
}
