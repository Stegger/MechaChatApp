/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.dal.facade;

import mechachatapp.dal.database.controller.PooledMessageDaoController;
import java.util.List;
import mechachatapp.be.Message;
import mechachatapp.be.User;
import mechachatapp.dal.database.connection.ConnectionPool;
import mechachatapp.dal.exceptions.DalException;

/**
 *
 * @author pgn
 */
public class MCDatabaseDalFacade implements IMechaChatDalFacade
{

    private PooledMessageDaoController poolMsgDaoController;

    public MCDatabaseDalFacade() throws DalException
    {
        poolMsgDaoController = new PooledMessageDaoController(ConnectionPool.getInstance());
    }

    @Override
    public Message createMessage(User sender, String msg) throws DalException
    {
        return poolMsgDaoController.createMessage(sender, msg);
    }

    @Override
    public void deleteMessage(Message message) throws DalException
    {
        poolMsgDaoController.deleteMessage(message);
    }

    @Override
    public List<Message> readAllMessages() throws DalException
    {
        return poolMsgDaoController.getAllMessages();
    }

}
