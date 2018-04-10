/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import mechachatapp.bll.exceptions.BllException;
import mechachatapp.gui.model.UserModel;

/**
 * FXML Controller class
 *
 * @author pgn
 */
public class MainController extends CommandableController implements Initializable
{

    @FXML
    private MenuItem menuRedo;
    @FXML
    private MenuItem menuUndo;

    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private Tab tabCollaborators;
    @FXML
    private Tab tabMessageLogs;

    private UserModel userModel;

    private CommandableController cmdCollaboratorView;
    private CommandableController cmdMessageLogView;

    private CommandableController commandableInView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    private void setCommandableInFocus(CommandableController cmdCtrl)
    {
        commandableInView = cmdCtrl;
        //Area should have bindings to the specific Commandable to control availability of the undo/redo menu buttons.
    }

    @FXML
    private void redo(ActionEvent event)
    {
        commandableInView.redo();
    }

    @FXML
    private void switchToCollaborators(Event event)
    {
        setCommandableInFocus(cmdCollaboratorView);
    }

    @FXML
    private void switchToMessageLog(Event event)
    {
        setCommandableInFocus(cmdMessageLogView);
    }

    @FXML
    private void undo(ActionEvent event)
    {
        commandableInView.undo();
    }

    void setUserModel(UserModel userModel)
    {
        this.userModel = userModel;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mechachatapp/gui/view/CollaboratorView.fxml"));
            tabCollaborators.setContent(loader.load());
            CollaboratorViewController collController = loader.getController();
            collController.setUserModel(userModel);
            cmdCollaboratorView = collController;

            loader = new FXMLLoader(getClass().getResource("/mechachatapp/gui/view/MessageLogView.fxml"));
            tabMessageLogs.setContent(loader.load());
            MessageLogViewController msgLogCtrl = loader.getController();
            msgLogCtrl.setUserModel(userModel);
            cmdMessageLogView = msgLogCtrl;
            setCommandableInFocus(cmdMessageLogView);
        } catch (IOException ex)
        {
            displayException(new BllException(ex.getMessage(), ex));
        }
    }

}
