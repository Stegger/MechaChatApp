/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.bll.message_logic;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import mechachatapp.be.Message;
import mechachatapp.dal.exceptions.DalException;
import mechachatapp.dal.facade.IMechaChatDalFacade;

/**
 *
 * @author pgn
 */
public class MessageLogObservable extends Observable
{

    private static final int MILLIS_BETWEEN_UPDATES = 3000;

    private IMechaChatDalFacade dalFacade;

    private final int LOG_ID;
    private int lastMessageId;
    private boolean isRunning;

    /**
     * Creates a Message Log Listner that listens to the log with the given
     * LOG_ID.
     *
     * @param LOG_ID    The id of the log to listen to.
     * @param dalFacade The facade with the access to the data of the message
     *                  log.
     */
    public MessageLogObservable(int LOG_ID, IMechaChatDalFacade dalFacade, int lastKnownMessageId, IMeassageLogListner... initialListeners)
    {
        this.LOG_ID = LOG_ID;
        this.dalFacade = dalFacade;
        this.lastMessageId = lastKnownMessageId;
        isRunning = true;
        for (IMeassageLogListner initialListener : initialListeners)
        {
            this.addMesssageLogListner(initialListener);
        }
        startListnerThread();
    }

    /**
     * Stops this message log listener from listening.
     */
    public void stopListningToMessageLog()
    {
        deleteObservers();
        isRunning = false;
    }

    /**
     * Add a listener to the message log being observed by this observable.
     *
     * @param listener The listener of the message log.
     */
    public void addMesssageLogListner(IMeassageLogListner listener)
    {
        this.addObserver(listener);
    }

    /**
     * Starts the observing thread.
     */
    private void startListnerThread()
    {
        Thread t = new Thread(()
                -> 
                {
                    while (isRunning)
                    {
                        try
                        {
                            List<Message> msgs = dalFacade.readAllMessages();
                            msgs.sort((Message o1, Message o2) -> o1.getId() - o2.getId());
                            for (Iterator<Message> msgIterator = msgs.iterator(); msgIterator.hasNext();)
                            {
                                Message msg = msgIterator.next();
                                if (msg.getId() > lastMessageId)
                                {
                                    lastMessageId = msg.getId();
                                    setChanged(); //Ipmortant this! Otherwise the observers will not update!
                                    notifyObservers(msg);
                                }
                            }
                            Thread.sleep(MILLIS_BETWEEN_UPDATES);
                        } catch (DalException | InterruptedException ex)
                        {
                            notifyObservers(ex);
                        }
                    }
        });
        t.setDaemon(true);
        t.start();
    }

}
