/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.dal.database.connection;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mechachatapp.dal.exceptions.DalException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author pgn
 */
public class ConnectionPool extends ObjectPool<Connection>
{

    private static ConnectionPool INSTANCE;

    private DBConnector dbConnector;


    public static synchronized ConnectionPool getInstance() throws DalException {
        if(INSTANCE == null)
            INSTANCE = new ConnectionPool();
        return INSTANCE;
    }

    private ConnectionPool() throws DalException
    {
        super();
        try
        {
            dbConnector = new DBConnector();
        } catch (IOException ex)
        {
            throw new DalException("Error creating connection pool to database", ex);
        }

    }

    @Override
    public void expire(Connection o) throws DalException
    {
        try
        {
            o.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean validate(Connection o)
    {
        try
        {
            return !o.isClosed();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected Connection create() throws DalException
    {
        try
        {
            return dbConnector.getConnection();
        } catch (SQLServerException ex)
        {
            throw new DalException(ex.getMessage(), ex);
        }
    }

}
