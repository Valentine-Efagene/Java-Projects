/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepadspeak;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 *
 * @author valentyne
 */
public class NotepadSpeakFXMLController implements Initializable {
    
    private Label label;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private Menu editMenu;
    @FXML
    private MenuItem copyMenuItem;
    @FXML
    private MenuItem cutMenuItem;
    @FXML
    private MenuItem pasteMenuItem;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private Menu formatMenu;
    @FXML
    private MenuItem speakMenuItem;
    @FXML
    private MenuItem fontMenuItem;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private TextArea textArea;
    
    File file;
    String s = "";
    Speech speech;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speech = new Speech();
    }    

    @FXML
    private void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        file = fileChooser.showOpenDialog(textArea.getScene().getWindow());
        
        if (file == null) {
            return;
        }
        
        try (
                InputStream in = Files.newInputStream(file.toPath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
            ) {
            String line = null;
        
            while ((line = reader.readLine()) != null) {
                textArea.appendText(line);
                textArea.appendText("\n");
            }
        } catch (IOException ex) {  
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText(Arrays.toString(ex.getStackTrace()));
            alert.show();
        }
    }

    @FXML
    private void speak(ActionEvent event) {
        speech.speak(textArea.getText());
    }
}