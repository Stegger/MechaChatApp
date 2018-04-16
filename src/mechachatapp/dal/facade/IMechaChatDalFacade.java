/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.dal.facade;

import java.util.List;
import mechachatapp.be.Message;
import mechachatapp.be.User;
import mechachatapp.dal.exceptions.DalException;

/**
 *
 * @author pgn
 */
public interface IMechaChatDalFacade
{

    public Message createMessage(User sender, String msg) throws DalException;

    public void deleteMessage(Message message) throws DalException;

    public List<Message> readAllMessages() throws DalException;

}
