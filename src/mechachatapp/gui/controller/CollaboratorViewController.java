/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.gui.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import mechachatapp.be.User;
import mechachatapp.gui.model.UserModel;
import mechachatapp.gui.view.SingleUserControl;

/**
 * FXML Controller class
 *
 * @author pgn
 */
public class CollaboratorViewController extends CommandableController implements Initializable
{

    @FXML
    private Button btnAllCollabsRight;
    @FXML
    private Button btnMyCollabsRight;
    @FXML
    private FlowPane paneAllCollaborators;
    @FXML
    private FlowPane paneMyCollaborators;
    @FXML
    private TextField txtSearchAllCollabs;
    @FXML
    private TextField txtSearchYourCollabs;

    private UserModel userModel;

    private Iterator<User> allUsersSource;

    public void setUserModel(UserModel userModel)
    {
        this.userModel = userModel;
        allUsersSource = userModel.getAllUsersIterator();

        initCollaboratorView(paneAllCollaborators, allUsersSource);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    @FXML
    private void handleAddCollaborator(ActionEvent event)
    {

    }

    @FXML
    private void handleRemoveCollaborator(ActionEvent event)
    {

    }

    private void initCollaboratorView(FlowPane pane, Iterator<User> userSsource)
    {
        pane.getChildren().clear();
        double width = pane.widthProperty().getValue();
        int nr = (int) (width / 200);
        for (int i = 0; i < nr && userSsource.hasNext(); i++)
        {
            User next = userSsource.next();
            Image image = userModel.getUserAvatar(next);
            SingleUserControl suc = new SingleUserControl(next, image);
            pane.getChildren().add(suc);
        }
    }

    @FXML
    private void nextAllCollabs(ActionEvent event)
    {
        User next = allUsersSource.next();
        Image image = userModel.getUserAvatar(next);
        SingleUserControl suc = new SingleUserControl(next, image);
        if (!paneAllCollaborators.getChildren().isEmpty())
        {
            paneAllCollaborators.getChildren().remove(0);
        }
        paneAllCollaborators.getChildren().add(suc);
    }

    @FXML
    private void nextMyCollabs(ActionEvent event)
    {
        
    }

}
