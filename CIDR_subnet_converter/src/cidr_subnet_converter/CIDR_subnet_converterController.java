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
        int pref = subnetToPrefix(tf_firstOctet.getText(), 
                tf_firstOctet.getText(), tf_firstOctet.getText(),
                tf_firstOctet.getText());
           tf_prefixLength.setText(new Integer(pref).toString());
    }

    @FXML
    private void toSubnetButtonAction(ActionEvent event) {
    }
    
    private int subnetToPrefix(String str1, String str2, String str3, String str4) {
        int count = 0,
            oct1 = 255,
            oct2 = 255,
            oct3 = 255,
            oct4 = 0;
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
        int d = 0;
        try{
            d = Integer.parseInt(tf.getText());
        }catch(NumberFormatException nfe){
            tf.setText(new Integer(0).toString());
        }
        
        if(d > 255) {
            tf.setText(new Integer(0).toString());
        }
    }
}
