/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.dal.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mechachatapp.be.Message;

/**
 *
 * @author pgn
 */
public class MessageDAO
{

    /**
     * Inserts a new Message in the Message table in the database and returns an
     * instance of the Message.
     *
     * @param con The connection to the database.
     * @param userId The ID of the user whom created the message.
     * @param msg The Text of the message.
     * @return A message object.
     * @throws SQLException
     */
    public Message createMessage(Connection con, int userId, String msg) throws SQLException
    {
        String sql = "INSERT INTO Message (UserId, Text) VALUES(?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, userId);
            ps.setString(2, msg);
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys())
            {
                rs.next();
                int id = rs.getInt(1);
                Message m = new Message(id, msg);
                return m;
            }
        }
    }
    
    public void deleteMessage(Connection con, Message message) throws SQLException
    {
        String sql = "DELETE FROM Message WHERE Id=?";
        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setInt(1, message.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Reads all messages from the Message table in the database.
     *
     * @param con The connection to the database.
     * @return A list of all messages in the database.
     * @throws SQLException
     */
    public List<Message> getAllMessages(Connection con) throws SQLException
    {
        String sql = "SELECT * FROM Message";
        try (Statement st = con.createStatement())
        {
            List<Message> messages = new ArrayList<>();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                messages.add(readMessageFromRow(rs));
            }
            return messages;
        }
    }

    /**
     * Reads a Message object from a row in a ResultSet.
     *
     * @param rs The ResutSet to read from.
     * @return A Message object.
     * @throws SQLException
     */
    private Message readMessageFromRow(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("Id");
        String text = rs.getString("Text");
        return new Message(id, text);
    }
    
}
