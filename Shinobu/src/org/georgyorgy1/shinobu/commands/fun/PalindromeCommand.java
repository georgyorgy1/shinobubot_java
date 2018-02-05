package org.georgyorgy1.shinobu.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PalindromeCommand extends Command
{
    public PalindromeCommand()
    {
        this.name = "palindrome";
    }

    @Override
    protected void execute(CommandEvent ce)
    {
        String arg = ce.getArgs();
        StringBuilder palindrome = new StringBuilder();

        if (palindrome.append(arg).reverse().toString().equals(arg))
        {
            ce.reply("It is a palindrome!");
        }

        else
        {
            ce.reply("It is not a palindrome!");
        }
    }
}
