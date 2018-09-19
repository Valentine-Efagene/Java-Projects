/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepadspeak;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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
    private MenuItem clearMenuItem;
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
    @FXML
    private ChoiceBox<String> fontChoiceBox;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        speech = new Speech();
        ObservableList<String> fontList = FXCollections.<String>observableArrayList(
                Font.SANS_SERIF);
        fontChoiceBox = new ChoiceBox<>(fontList);
        fontChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                textArea.setFont();
            }
            
        });
    }    

    @FXML
    private void openFile(ActionEvent event) {
        file = getFile("Open");
        
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

    @FXML
    private void about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is a simple notepad app that can speak out text.\n"
                + "Creator: Valentine Edesiri Efagene (VEE).\n"
                + "For inquiries and suggestions, you may contact me via\n"
                + "email: efagenevalentine@gmail.com\n"
                + "facebook: Valentine Efagene\n"
                + "whatsapp: 07053229765\n\n");
        
        alert.show();
    }

    @FXML
    private void copy(ActionEvent event) {
        textArea.copy();
    }

    @FXML
    private void cut(ActionEvent event) {
        textArea.cut();
    }

    @FXML
    private void paste(ActionEvent event) {
        textArea.paste();
    }

    @FXML
    private void clear(ActionEvent event) {
        textArea.deleteText(0, textArea.getText().length());
    }
    
    @FXML
    private void save(ActionEvent event) {
        if( file == null )
            file = getSaveFile("Save");
        
        try (PrintWriter p = new PrintWriter(file.getPath())) {
            p.print(textArea.getText());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotepadSpeakFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveAs(ActionEvent event) {
        file = getSaveFile( "Save As" );
        
        try (PrintWriter p = new PrintWriter(file.getPath())) {
            p.print(textArea.getText());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotepadSpeakFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public File getFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());
        return file;
    }
    
    public File getSaveFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showSaveDialog(textArea.getScene().getWindow());
        return file;
    }

    @FXML
    private void newFile(ActionEvent event) {
        if (file != null) {
            file = null;
        }
        
        textArea.deleteText(0, textArea.getText().length());
    }
}