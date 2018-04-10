/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.bll.facade;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import mechachatapp.be.Message;
import mechachatapp.be.User;
import mechachatapp.bll.exceptions.BllException;
import mechachatapp.bll.message_logic.IMeassageLogListner;
import mechachatapp.bll.message_logic.MessageLogObservable;

/**
 *
 * @author pgn
 */
public interface IMechaChatLogicFacade
{

    public void deleteMessage(Message message) throws BllException;

    public List<Message> getAllMessages() throws BllException;

    public ArrayList<User> getAllUsers();

    public Image getAvatar(User next);

    public MessageLogObservable listenForMessages(IMeassageLogListner messageListener, int lastKnownId);

    public User logInUser(String userName, String password) throws BllException;

    public Message logMessage(String msg) throws BllException;

    public User createUSer(String userName, String email, String password) throws BllException;

}
