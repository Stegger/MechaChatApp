/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechachatapp.gui.controller;

import java.util.Stack;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import mechachatapp.bll.exceptions.BllException;
import mechachatapp.gui.model.command.ICommand;

/**
 *
 * @author pgn
 */
public abstract class CommandableController
{
    

    private Stack<ICommand> undos;
    private Stack<ICommand> redos;

    
    
    public CommandableController()
    {
        undos = new Stack<>();
        redos = new Stack<>();
    }

    public void displayException(BllException ex)
    {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Invokes the command and adds it to the undo stack. Also clears the redo stack.
     * @param command 
     */
    public void issueCommand(ICommand command)
    {
        try
        {
            command.execute();
            undos.push(command);
            redos.clear();
        } catch (BllException ex)
        {
            displayException(ex);
        }
    }

    /**
     * Undo the last command to be invoked
     */
    public void undo()
    {
        if (!undos.empty())
        {
            try
            {
                ICommand cmd = undos.pop();
                cmd.undo();
                redos.push(cmd);
            } catch (BllException ex)
            {
                displayException(ex);
            }
        }
    }

    /**
     * Redo the last undone command.
     */
    public void redo()
    {
        if (!redos.empty())
        {
            try
            {
                ICommand cmd = redos.pop();
                cmd.execute();
                undos.push(cmd);
            } catch (BllException ex)
            {
                displayException(ex);
            }
        }
    }

}