/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cidr_subnet_converter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author valentyne
 */
public class CIDR_subnet_converterController implements Initializable {

    @FXML
    private Button btn_toPrefixLength;
    @FXML
    private Button btn_toSubnet;
    @FXML
    private TextField tf_firstOctet;
    @FXML
    private TextField tf_secondOctet;
    @FXML
    private TextField tf_thirdOctet;
    @FXML
    private TextField tf_fourthOctet;
    @FXML
    private TextField tf_prefixLength;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void toPrefixLengthButtonAction(ActionEvent event) {
        if(!(isInputValid(tf_firstOctet) || isInputValid(tf_secondOctet) || 
                isInputValid(tf_thirdOctet) || isInputValid(tf_fourthOctet))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("The octets must be numbers from 0 to 255!");
            alert.show();
            return;
        }
        
        int pref = subnetToPrefix(tf_firstOctet.getText(), 
                tf_firstOctet.getText(), tf_firstOctet.getText(),
                tf_firstOctet.getText());
           tf_prefixLength.setText(new Integer(pref).toString());
    }

    @FXML
    private void toSubnetButtonAction(ActionEvent event) {
        int d = 0;
        char[] s = new char[32];
        
        try{
            d = Integer.parseInt(tf_prefixLength.getText());
            
        }catch(NumberFormatException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Please enter an integer!");
            alert.show();
            tf_prefixLength.setText("");
            return;
        }
        
        if(d <= 32 && d >= 0) {
            for(int i = 0; i < d; i++) {
                s[i] = '1';
            }
            
            for(int i = d; i < 32; i++) {
                s[i] = '0';
            }
            
            char[][] a = new char[4][8];
            
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 8; j++) {
                    a[i][j] = s[i * 8 + j];
                }
            }
            
            int result = Integer.parseInt(new String(a[0]), 2);
            tf_firstOctet.setText(new Integer(result).toString());
            result = Integer.parseInt(new String(a[1]), 2);
            tf_secondOctet.setText(new Integer(result).toString());
            result = Integer.parseInt(new String(a[2]), 2);
            tf_thirdOctet.setText(new Integer(result).toString());
            result = Integer.parseInt(new String(a[3]), 2);
            tf_fourthOctet.setText(new Integer(result).toString());
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Prefix length must be an integer from 0 to 32!");
            alert.show();
            tf_prefixLength.setText("");
            return;
        }
    }
    
    private int subnetToPrefix(String str1, String str2, String str3, String str4) {
        int count = 0,
            oct1 = Integer.parseInt(str1),
            oct2 = Integer.parseInt(str2),
            oct3 = Integer.parseInt(str3),
            oct4 = Integer.parseInt(str4);
        String result = Integer.toBinaryString(oct1) 
                + Integer.toBinaryString(oct2) 
                + Integer.toBinaryString(oct3) 
                + Integer.toBinaryString(oct1);
        
        for(char a : result.toCharArray()) {
            if(a == '1') {
                count++;
            }
        }
        
        return count;
    }

    @FXML
    private void onOctetTFAction(ActionEvent event) {
        TextField tf = (TextField)event.getSource();
        if (isInputValid(tf)) {
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("The octets must be numbers from 0 to 255!");
            alert.show();
    }
    
    public boolean isInputValid(TextField tf) {
        int d = 0;
        
        try{
            d = Integer.parseInt(tf.getText());
            
            if(d > 255) {
                tf.setText("");
                return false;
            }
        }catch(NumberFormatException nfe){
            tf.setText("");
            return false;
        }
        
        return true;
    }

    @FXML
    private void aboutButtonAction(ActionEvent event) {
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
}
