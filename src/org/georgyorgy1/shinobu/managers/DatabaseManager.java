package org.georgyorgy1.shinobu.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.georgyorgy1.shinobu.managers.enums.DatabaseAction;

public class DatabaseManager
{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Logger logger;
    private String url;
    
    public DatabaseManager()
    {
        logger = LoggerFactory.getLogger(DatabaseManager.class.getName());
        url = "jdbc:sqlite:files/shinobu.db";
    }
    
    public Connection openConnection()
    {
        try
        {
            connection = DriverManager.getConnection(url);
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        return connection;
    }
    
    public PreparedStatement openPreparedStatement(Connection connection, String statement)
    {
        try
        {
            preparedStatement = connection.prepareStatement(statement);
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
        
        return preparedStatement;
    }
    
    public ResultSet openResultSet(PreparedStatement preparedStatement, DatabaseAction action)
    {
        if (action == DatabaseAction.RETRIEVE)
        {
            try
            {
                resultSet = preparedStatement.executeQuery();
            } 
            
            catch (SQLException exception)
            {
                logger.error(exception.toString());
            }
        }
        
        return resultSet;
    }
    
    public void closeConnection(Connection connection)
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
    
    public void closePreparedStatement(PreparedStatement preparedStatement)
    {
        try
        {
            preparedStatement.close();
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
    }
    
    public void closeResultSet(ResultSet resultSet)
    {
        try
        {
            resultSet.close();
        }
        
        catch (SQLException exception)
        {
            logger.error(exception.toString());
        }
    }
}