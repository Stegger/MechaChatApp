/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import mechachatapp.be.Message;
import mechachatapp.bll.exceptions.BllException;
import mechachatapp.gui.model.MechaChatLogModel;
import mechachatapp.gui.model.UserModel;
import mechachatapp.gui.model.command.CreateMessageCommand;

/**
 *
 * @author pgn
 */
public class MessageLogViewController extends CommandableController implements Initializable
{

    @FXML
    private ListView<Message> lstMessages;
    @FXML
    private TextField txtMessage;

    private MechaChatLogModel messageModel;
    private UserModel userModel;

    private KeyCombination keysUndo;
    private KeyCombination keysRedo;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        keysUndo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        keysRedo = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);

        try
        {
            messageModel = new MechaChatLogModel();
            instantiateListViewCellFactory();
            lstMessages.setItems(messageModel.getMessages());
            initKeyHandling();
        } catch (BllException ex)
        {
            displayException(ex);
        }
    }

    private void instantiateListViewCellFactory()
    {
        lstMessages.setCellFactory((ListView<Message> param)
                -> 
                {
                    ListCell<Message> cell = new ListCell<Message>()
                    {
                        @Override
                        protected void updateItem(Message item, boolean empty)
                        {
                            super.updateItem(item, empty);
                            if (!empty && item != null)
                            {
                                setText("#" + item.getId() + ": " + item.getText());
                            } else
                            {
                                setText(null);
                                setGraphic(null);
                            }
                        }

                    };
                    return cell;
        });
    }

    private void initKeyHandling()
    {
        txtMessage.getParent().addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event)
                -> 
                {
                    if (keysUndo.match(event))
                    {
                        System.out.println("Ctrl + z");
                        undo();
                    } else if (keysRedo.match(event))
                    {
                        System.out.println("Ctrl + y");
                        redo();
                    }
        });
    }

    @FXML
    private void handleSendMessage(ActionEvent event)
    {
        String txt = txtMessage.getText();
        CreateMessageCommand cmdCreate = new CreateMessageCommand(messageModel, txt);
        issueCommand(cmdCreate);
    }

    void setUserModel(UserModel userModel)
    {
        this.userModel = userModel;
        messageModel.setUserModel(userModel);
    }

}
