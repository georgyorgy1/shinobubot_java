package org.georgyorgy1.shinobu.events.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.DatabaseManager;
import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;
import org.georgyorgy1.shinobu.utilities.CurrentDateTime;

public class Infraction
{
    private Logger logger;
    private DatabaseManager manager;
    private CurrentDateTime now;
    
    public Infraction()
    {
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
        this.manager = new DatabaseManager();
        this.now = new CurrentDateTime();
    }
    
    public String getRemovalMessage(Guild guild, User user, Role role)
    {
        Connection connection = manager.openConnection();
        String statement = "SELECT case_number FROM infractions WHERE guild = ? AND user_id = ? AND mod_action = ? AND end_date = \"None\" ORDER BY case_number DESC LIMIT 1";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, user.getId());
            preparedStatement.setString(3, "Role Assign (" + role.getName() + ")");
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        int caseNumber = -1;
        String dateTime = now.getDateTime();
        
        try
        {
            while (resultSet.next())
            {
                caseNumber = resultSet.getInt("case_number");
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
        }
        
        String top = "```css" + "\n";
        String usernameFormat = "Username: " + user.getName() + "#" + user.getDiscriminator() + "\n";
        String userIdFormat = "User ID: " + user.getId() + "\n";
        String modActionFormat = "Action: Role Removal (" + role.getName() + ")" + "\n";
        String dateTimeFormat = "Date and Time: " +  dateTime + "\n";
        String bottom = "```";
        
        if (caseNumber != -1)
        {
            statement = "UPDATE infractions SET end_date = ? WHERE case_number = ?";
            preparedStatement = manager.openPreparedStatement(connection, statement);
            
            try
            {
                preparedStatement.setString(1, dateTime);
                preparedStatement.setInt(2, caseNumber);
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
            
            return top + "Case Number: " + Integer.toString(caseNumber) + "\n" + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
        }
        
        else
        {
            manager.closeConnection(connection);
            return top + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
        }
    }
    
    public String getUnbanMessage(Guild guild, User user)
    {
        Connection connection = manager.openConnection();
        String statement = "SELECT case_number FROM infractions WHERE guild = ? AND user_id = ? AND mod_action = ? AND end_date = \"None\" ORDER BY case_number DESC LIMIT 1";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, user.getId());
            preparedStatement.setString(3, "Ban");
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        int caseNumber = -1;
        String dateTime = now.getDateTime();
        
        try
        {
            while (resultSet.next())
            {
                caseNumber = resultSet.getInt("case_number");
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
        }
        
        String top = "```css" + "\n";
        String usernameFormat = "Username: " + user.getName() + "#" + user.getDiscriminator() + "\n";
        String userIdFormat = "User ID: " + user.getId() + "\n";
        String modActionFormat = "Action: Unban" + "\n";
        String dateTimeFormat = "Date and Time: " +  dateTime + "\n";
        String bottom = "```";
        
        if (caseNumber != -1)
        {
            statement = "UPDATE infractions SET end_date = ? WHERE case_number = ?";
            preparedStatement = manager.openPreparedStatement(connection, statement);
            
            try
            {
                preparedStatement.setString(1, dateTime);
                preparedStatement.setInt(2, caseNumber);
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
            
            return top + "Case Number: " + Integer.toString(caseNumber) + "\n" + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
        }
        
        else
        {
            manager.closeConnection(connection);
            return top + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
        }
    }
    
    public int getLatestCaseNumber(Guild guild)
    {
        String statement =  "SELECT case_number FROM infractions WHERE guild = ? ORDER BY case_number DESC LIMIT 1";
        Connection connection = manager.openConnection();
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, guild.getId());
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        int caseNumber = -1;
        
        try
        {
            while (resultSet.next())
            {
                caseNumber = resultSet.getInt("case_number");
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
        
        return caseNumber;
    }
    
    public String getPrivateLogMessage(GuildMemberRoleAddEvent event)
    {
        User user = event.getMember().getUser();
        String username = "Username: " + user.getName() + "#" + user.getDiscriminator();
        String userId = "User ID: " + user.getId();
        Role role = event.getRoles().get(0);
        String modAction = "Role Assign (" + role.getName() + ")";
        String message = "To publish this case to public mod log, type !reason <reason>.";
        
        return username + "\n" + userId + "\n" + modAction + "\n\n" + message;
    }
    
    public String getPrivateLogMessage(GuildBanEvent event)
    {
        User user = event.getUser();
        String username = "Username: " + user.getName() + "#" + user.getDiscriminator();
        String userId = "User ID: " + user.getId();
        String modAction = "Ban";
        String message = "To publish this case to public mod log, type !reason <reason>.";
        
        return username + "\n" + userId + "\n" + modAction + "\n\n" + message;
    }
    
    public String getPublicLogMessage(int caseNumber, String reason)
    {
        Connection connection = manager.openConnection();
        String statement = "UPDATE infractions SET reason = ? WHERE case_number = ?";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, reason);
            preparedStatement.setString(2, Integer.toString(caseNumber));
            preparedStatement.executeUpdate();
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        finally
        {
            manager.closePreparedStatement(preparedStatement);
        }
        
        statement = "SELECT username, user_id, mod_action, start_date FROM infractions WHERE case_number = ?";
        preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setInt(1, caseNumber);
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        String username = "";
        String userId = "";
        String modAction = "";
        String dateTime = "";

        try
        {
            while (resultSet.next())
            {
                username = resultSet.getString("username");
                userId = resultSet.getString("user_id");
                modAction = resultSet.getString("mod_action");
                dateTime = resultSet.getString("start_date");
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
        
        String top = "```css" + "\n";
        String caseNumberFormat = "Case Number: " + Integer.toString(caseNumber) + "\n";
        String usernameFormat = "Username: " + username + "\n";
        String userIdFormat = "User ID: " + userId + "\n";
        String modActionFormat = "Action: " + modAction + "\n";
        String reasonFormat = "Reason: " + reason + "\n";
        String dateTimeFormat = "Date and Time: " + dateTime + "\n";
        String bottom = "```";

        return top + caseNumberFormat + usernameFormat + userIdFormat + modActionFormat + reasonFormat + dateTimeFormat + bottom;
    }
    
    public String getPublicLogMessage(GuildBanEvent event)
    {
        Guild guild = event.getGuild();
        User user = event.getUser();
        String username = user.getName() + "#" + user.getDiscriminator();
        String userId = user.getId();
        String modAction = "Ban";
        String reason = "None";
        String messageId = "None";
        String startDate = now.getDateTime();
        String endDate = "None";
        Connection connection = manager.openConnection();
        String statement = "INSERT INTO infractions (guild, username, user_id, mod_action, reason, message_id, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, userId);
            preparedStatement.setString(4, modAction);
            preparedStatement.setString(5, reason);
            preparedStatement.setString(6, messageId);
            preparedStatement.setString(7, startDate);
            preparedStatement.setString(8, endDate);
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
        
        String top = "```css" + "\n";
        String caseNumberFormat = "Case Number: " + getLatestCaseNumber(guild) + "\n";
        String usernameFormat = "Username: " + username + "\n";
        String userIdFormat = "User ID: " + userId + "\n";
        String modActionFormat = "Action: " + modAction + "\n";
        String dateTimeFormat = "Date and Time: " + startDate + "\n";
        String bottom = "```";

        return top + caseNumberFormat + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
    }
    
    public String getPublicLogMessage(GuildMemberRoleAddEvent event)
    {
        Guild guild = event.getGuild();
        User user = event.getMember().getUser();
        String username = user.getName() + "#" + user.getDiscriminator();
        String userId = user.getId();
        Role role = event.getRoles().get(0);
        String modAction = "Role Assign (" + role.getName() + ")";
        String reason = "None";
        String messageId = "None";
        String startDate = now.getDateTime();
        String endDate = "None";
        Connection connection = manager.openConnection();
        String statement = "INSERT INTO infractions (guild, username, user_id, mod_action, reason, message_id, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, userId);
            preparedStatement.setString(4, modAction);
            preparedStatement.setString(5, reason);
            preparedStatement.setString(6, messageId);
            preparedStatement.setString(7, startDate);
            preparedStatement.setString(8, endDate);
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
        
        String top = "```css" + "\n";
        String caseNumberFormat = "Case Number: " + getLatestCaseNumber(guild) + "\n";
        String usernameFormat = "Username: " + username + "\n";
        String userIdFormat = "User ID: " + userId + "\n";
        String modActionFormat = "Action: " + modAction + "\n";
        String dateTimeFormat = "Date and Time: " + startDate + "\n";
        String bottom = "```";

        return top + caseNumberFormat + usernameFormat + userIdFormat + modActionFormat + dateTimeFormat + bottom;
    }
    
    public String getMessageId(int caseNumber)
    {
        String statement = "SELECT message_id FROM infractions WHERE case_number = ?";
        Connection connection = manager.openConnection();
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);

        try
        {
            preparedStatement.setInt(1, caseNumber);
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        String messageId = "";
        
        try
        {
            while (resultSet.next())
            {
                messageId = resultSet.getString("message_id");
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
        
        return messageId;
    }

    public void setMessageId(String messageId, int caseNumber)
    {
        String statement = "UPDATE infractions SET message_id = ? WHERE case_number = ?";
        Connection connection = manager.openConnection();
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        
        try
        {
            preparedStatement.setString(1, messageId);
            preparedStatement.setString(2, Integer.toString(caseNumber));
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
    }
    
    public boolean isRoleLoggable(Guild guild, Role role)
    {
        Connection connection = manager.openConnection();
        String statement = "SELECT role_name FROM listed_roles WHERE guild = ? AND role_name = ?";
        PreparedStatement preparedStatement = manager.openPreparedStatement(connection, statement);
        boolean roleLoggable = false;

        try
        {
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, role.getName());
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        ResultSet resultSet = manager.openResultSet(preparedStatement, DatabaseAction.RETRIEVE);
        
        try
        {
            while (resultSet.next())
            {
                if (resultSet.getString("role_name").equals(role.getName()))
                {
                    roleLoggable = true;
                }
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
        
        return roleLoggable;
    }
}