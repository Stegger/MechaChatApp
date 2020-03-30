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
import mechachatapp.dal.exceptions.DalException;
import mechachatapp.dal.facade.IMechaChatDalFacade;
import mechachatapp.dal.facade.MCDatabaseDalFacade;

/**
 *
 * @author pgn
 */
public class MCLogicFacade implements IMechaChatLogicFacade
{

    private static MCLogicFacade INSTANCE;
    private IMechaChatDalFacade dalFacade;

    private MCLogicFacade() throws BllException
    {
        try
        {
            dalFacade = new MCDatabaseDalFacade();
        } catch (DalException ex)
        {
            throw new BllException("Could not establish a connection to the data access layer.", ex);
        }
    }

    public synchronized static MCLogicFacade getInstance() throws BllException
    {
        if (INSTANCE == null)
        {
            INSTANCE = new MCLogicFacade();
        }
        return INSTANCE;
    }

    @Override
    public void deleteMessage(Message message) throws BllException
    {
        try
        {
            dalFacade.deleteMessage(message);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Message> getAllMessages() throws BllException
    {
        try
        {
            return dalFacade.readAllMessages();
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage(), ex);
        }
    }

    @Override
    public ArrayList<User> getAllUsers()
    {
        //DUMMY METHOD
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(new User(1, "Jeppe", "jml@easv.dk"));
        allUsers.add(new User(2, "Mads", "mjl@easv.dk"));
        allUsers.add(new User(3, "Bent", "bhp@easv.dk"));
        allUsers.add(new User(4, "Lars", "lb@easv.dk"));
        allUsers.add(new User(5, "Ole", "oe@easv.dk"));
        allUsers.add(new User(6, "Henrik", "hk@easv.dk"));
        allUsers.add(new User(7, "Anne-Mette", "amt@easv.dk"));
        return allUsers;
    }

    @Override
    public Image getAvatar(User next)
    {
        //DUMMY METHOD
        return new Image("file:C:\\Users\\pgn\\Documents\\NetBeansProjects\\MechaChatApp\\src\\mechachatapp\\gui\\images\\defaultAvatar.jpg");
    }

    @Override
    public MessageLogObservable listenForMessages(IMeassageLogListner messageListener, int lastKnownId)
    {
        MessageLogObservable messageLogObservable = new MessageLogObservable(0, dalFacade, lastKnownId, messageListener);
        return messageLogObservable;
    }

    @Override
    public User logInUser(String userName, String password) throws BllException
    {
        //DUMMY METHOD
        return new User(1, "Stegger", "pgn@easv.dk");
    }

    @Override
    public Message logMessage(User sender, String msg) throws BllException
    {
        try
        {
            return dalFacade.createMessage(sender, msg);
        } catch (DalException ex)
        {
            throw new BllException(ex.getMessage(), ex);
        }

    }

    @Override
    public User createUSer(String userName, String email, String password) throws BllException
    {
        //DUMMY METHOD
        return new User(1, userName, email);
    }

}
