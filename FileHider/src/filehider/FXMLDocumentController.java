/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filehider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Edesiri .V. Efagene
 */
public class FXMLDocumentController implements Initializable {
    
    File file;
    Speech speech;
    String choice;
    private File hideFile;
    boolean speak = false;
    String nameOfFile = null;
    
    @FXML
    private TextField fileTextField;
    @FXML
    private Button hideButton;
    @FXML
    private TextField keyPasswordField;
    @FXML
    private TextField hideFileLocationTextField;
    @FXML
    private TextField hiddenFileLocationTextField;
    @FXML
    private TextField saveFileLocationTextField;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem usageMenuItem;
    @FXML
    private Button fileButton;
    @FXML
    private Button unhideButton;
    @FXML
    private Button saveFileLocationButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button retrieveButton;
    @FXML
    private Button hideFileButton;
    @FXML
    private RadioMenuItem audioRadioMenuItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        speech = new Speech();
    }    

    @FXML
    private void closeAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void fileButtonAction(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("File");
        choices.add("Folder");
        ChoiceDialog<String> dialog = new ChoiceDialog("File", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText(null);
        dialog.setContentText("What do you want to hide?");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> choice = letter);
        
        if(choice != null){
            if(choice.equals("Folder")){
                DirectoryChooser directoryChooser = new DirectoryChooser();
                file = directoryChooser.showDialog(hideButton.getScene().getWindow());
            }else if(choice.equals("File")){
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(hideButton.getScene().getWindow());
            }
        }
        
        if(file != null){
            fileTextField.setText(file.getAbsolutePath().replace("\\", "/"));
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("No existing file selected");
            alert.show();
        }
    }

    @FXML
    private void hideButtonAction(ActionEvent event) {
        Path path = Paths.get(fileTextField.getText());
        
        if(!Files.exists(path)){
            if(speak)
            {
                speech.speak("Such a file does not exist");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Such a file does not exist");
                alert.setContentText(null);
                alert.show();
                return;
            }
        }
        
        file = new File(fileTextField.getText());
        
        if(file.isFile() || file.isDirectory()){
            try {
                String command = "attrib +H " + "\"" + fileTextField.getText() + "\"";
                System.out.println(command);
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec(command);
                process.waitFor();
                
                if(process.exitValue() == 0)
                {
                    if(speak)
                    {
                        speech.speak("File Successfully Hidden");
                    }else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("File Hidden");
                        alert.setContentText(null);
                        alert.show();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("ERROR");
                    alert.setContentText(getErrorMessage(process));
                    alert.show();
                }
            } catch (IOException | InterruptedException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText(Arrays.toString(ex.getStackTrace()));
                alert.show();
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(file == null){
            if(speak)
            {
                speech.speak("File could not be accessed");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("File could not be accessed");
                alert.show();
            }
        }
    }

    @FXML
    private void unhideButtonAction(ActionEvent event) {
        Path path = Paths.get(fileTextField.getText());
        
        if(!Files.exists(path)){
            if(speak)
            {
                speech.speak("Such a file does not exist");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Such a file does not exist");
                alert.setContentText(null);
                alert.show();
                return;
            }
        }
        
        file = new File(fileTextField.getText());
        
        if(file.isFile() || file.isDirectory()){
            try {
                String command = "attrib -H " + "\"" + fileTextField.getText() + "\"";
                System.out.println(command);
                Process process = Runtime.getRuntime().exec(command);
                process.waitFor();
                
                if(process.exitValue() == 0)
                {
                    if(speak)
                    {
                        speech.speak("File successfully unhidden");
                    }else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("File Unhidden");
                        alert.setContentText(null);
                        alert.show();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("ERROR");
                    alert.setContentText(getErrorMessage(process));
                    alert.show();
                }
                
            } catch (IOException | InterruptedException ex)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText(Arrays.toString(ex.getStackTrace()));
                alert.show();
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(file == null){
            if(speak)
            {
                speech.speak("File could not be accessed");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("File could not be accessed");
                alert.show();
            }
        }
    }

    @FXML
    private void locationOfHideFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        hideFile = fileChooser.showOpenDialog(hideButton.getScene().getWindow());
        
        if(hideFile != null){
            hideFileLocationTextField.setText( "\"" + hideFile.toString().replace("\\", "/") + "\"");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("You didn't choose a file");
            alert.show();
        }
    }
    
    @FXML
    private void whereToSaveLocationOfHiddenFileButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        hideFile = directoryChooser.showDialog(hideButton.getScene().getWindow());
        
        if(hideFile != null){
            String nameOfHideFile = null;
            
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Name of Hide-File");
            textInputDialog.setHeaderText(null);
            textInputDialog.setContentText("Enter the name of hide-file");
            Optional<String> result = textInputDialog.showAndWait();
            
            if(result.isPresent()){
                nameOfHideFile = result.get();
                nameOfFile = nameOfHideFile;
            }
            
            hideFile = new File(hideFile.getAbsolutePath().replace("\\", "/") + "/" +nameOfHideFile+ ".ser");
            
            if(hideFile != null){
                hideFileLocationTextField.setText( "\"" + hideFile.toString().replace("\\", "/") + "\"");
                saveFileLocationTextField.setText( "\"" + hideFile.toString().replace("\\", "/") + "\"");
            }
        }else{
            if(speak)
            {
                speech.speak("You didn't choose a file");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("You didn't choose a file");
                alert.show();
            }
        }
    }

    @FXML
    private void retrieveLocationButtonAction(ActionEvent event) {
        String name = hideFileLocationTextField.getText();
        
        Path path = Paths.get(name);
        
        if(!Files.exists(path)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Such a file does not exist");
                    alert.setContentText(null);
                    alert.show();
            return;
        }
        
        hideFile = new File(name);
        String fileName = hideFile.getAbsolutePath();
        String cipherKeyLocation;
        cipherKeyLocation = hideFile.getParent().replace("\\", "\\\\").concat("\\\\") + hideFile.getName().replaceFirst(".ser", "") + "CipherKey.ser";
        System.out.println("Retrieve " + cipherKeyLocation);
        Security security = new Security(fileName, cipherKeyLocation);
        security.decrypt();
        
        try(FileInputStream fileIn = new FileInputStream(hideFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);)
        {
            HiddenFile hiddenFile = (HiddenFile)in.readObject();
            
            if(hiddenFile != null){
                hiddenFile.authenticate(keyPasswordField.getText());
            
                if(hiddenFile.getAuthenticated())
                {
                    fileTextField.setText(hiddenFile.getPath());
                    hiddenFileLocationTextField.setText(hiddenFile.getPath());
                }
            }
        } catch (FileNotFoundException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText(Arrays.toString(ex.getStackTrace()));
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            alert.show();
        } catch (IOException | ClassNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText(Arrays.toString(ex.getStackTrace()));
            alert.show();
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        security.encrypt();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        HiddenFile hiddenFile = new HiddenFile(keyPasswordField.getText(), fileTextField.getText());
        String fileName = hideFile.getAbsolutePath();
        String cipherKeyLocation;
        //Security security = ;
        
        try(FileOutputStream fileOut = new FileOutputStream(hideFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);)
        {
            out.writeObject(hiddenFile);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cipherKeyLocation = hideFile.getParent().replace("\\", "\\\\").concat("\\\\").concat(hideFile.getName().replaceFirst(".ser", "")).concat("CipherKey.ser");
        System.out.println(cipherKeyLocation);
        Security security = new Security(fileName, cipherKeyLocation);
        security.encrypt();
    }
    
    @FXML
    private void usageMenuItemAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Usage");
        alert.setContentText("NOTE: The app supports drag and drop on the\n"
                + "fileLocation & location of hideFile textfields\n\n"
                + "To enter specify save location:\n"
                + "1. "
                + "Enter file path by text (a .ser file).\n"
                + "2. "
                + "(Recommended) Click on the folder chooser button, select\n"
                + "    the folder, then enter just the file name without \n"
                + "    extension in the resulting dialog.\n"
                + "\n"
                + "To save file location:\n"
                + "After entering the file path in the \"File/Folder\" text field,\n"
                + "choose the desired save location, enter the desired key and press save\n"
                + "\n"
                + "To retrieve file location:\n"
                + "Get file location in the \"Location of hide file\" text field\n"
                + "Enter the key.\n"
                + "Press the \"Retieve\" button.");
        
        alert.show();
    }
    
    @FXML
    private void aboutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is an app to hide files and folders.\n"
                + "Creator: Valentine Edesiri Efagene (VEE).\n"
                + "For inquiries and suggestions, you may contact me via\n"
                + "email: efagenevalentine@gmail.com\n"
                + "facebook: Valentine Efagene\n"
                + "whatsapp: 07053229765\n\n"
                + "HAPPY HIDING.");
        
        alert.show();
    }
    
    public String getErrorMessage(Process p)
    {
    StringBuilder sb = new StringBuilder();
    
    try
    {
    BufferedReader stdErr  = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    String s = null;
    
    while((s = stdErr.readLine()) != null)
    {
    sb.append(s);
    }
    } catch (IOException ex)
    {
    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return sb.toString();
    }
    
        @FXML
    private void dragOver(DragEvent event)
    {
        Dragboard dragboard = event.getDragboard();
        
        if(dragboard.hasFiles())
        {
            event.acceptTransferModes(TransferMode.LINK);
        }
        
        event.consume();
    }

    @FXML
    private void dragDropped(DragEvent e)
    {
        Dragboard dragboard = e.getDragboard();
        
        if(dragboard.hasFiles())
        {
            hideFileLocationTextField.setText(dragboard.getFiles().get(0).getAbsolutePath().replace("\\", "/"));
        }
    }

    @FXML
    private void dragDroppedForFile(DragEvent e)
    {
        Dragboard dragboard = e.getDragboard();
        
        if(dragboard.hasFiles())
        {
            fileTextField.setText(dragboard.getFiles().get(0).getAbsolutePath().replace("\\", "/"));
        }
    }

    @FXML
    private void setAudio(ActionEvent event)
    {
        speak = !speak;
    }
}
