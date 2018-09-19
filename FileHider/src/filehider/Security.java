/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filehider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Edesiri .V. Efagene
 */
public class Security
{

    /**
     * @param args the command line arguments
     */ 
    private String keyLocation;
    private String fileName;
    private Key key = null;
    private Cipher cipher = null;
    
    public Security( String keyLocation )
    {
        this.keyLocation = keyLocation;
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyLocation)))
        {
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            key = (Key)in.readObject();
        }catch (IOException | ClassNotFoundException | ClassCastException ex)
        {
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyLocation)))
            {
                KeyGenerator generator = KeyGenerator.getInstance("DES");
                generator.init(new SecureRandom());
                key = generator.generateKey();
                out.writeObject(key);
            } catch (NoSuchAlgorithmException | IOException ex1)
            {
                ex1.printStackTrace();
            }
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String encrypt( String plainText )
    {
        String cipherText = null;
        byte[] cipherBytes;
        
        try
        {
            byte[] plainBytes = plainText.getBytes("UTF8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherBytes = cipher.doFinal(plainBytes);
            //cipherText = new String(cipherBytes, "ISO-8859-1");
            BASE64Encoder encoder = new BASE64Encoder();
            cipherText = encoder.encode(cipherBytes);
            System.out.println(plainText);
            System.out.println(cipherText);
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cipherText;
    }
    
    public String decrypt( String cipherText )
    {
        String plainText = null;
        byte[] plainBytes = null;
        byte[] cipherBytes = null;
        
        try
        {
            cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder decoder = new BASE64Decoder();
            cipherBytes = decoder.decodeBuffer(cipherText);
            plainBytes = cipher.doFinal(cipherBytes);
            plainText = new String(plainBytes, "UTF8");
            //plainText = (String)plainBytes.toString();
            //plainText = new String(plainBytes, "ISO-8859-1
            BASE64Encoder encoder = new BASE64Encoder();
            cipherText = encoder.encode(cipherBytes);
            System.out.println(cipherText);
            System.out.println(plainText);
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cipherText;
    }
    
    public Security( String fileName, String keyLocation )
    {
        this.fileName = fileName;
        this.keyLocation = keyLocation;
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(keyLocation)))
        {
            //cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            key = (Key)in.readObject();
        }catch (IOException | ClassNotFoundException | ClassCastException ex)
        {
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyLocation)))
            {
                KeyGenerator generator = KeyGenerator.getInstance("DES");
                generator.init(new SecureRandom());
                key = generator.generateKey();
                out.writeObject(key);
            } catch (NoSuchAlgorithmException | IOException ex1)
            {
                ex1.printStackTrace();
            }
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void encrypt()
    {
        byte[] cipherText = null;
        byte[] raw = null;
        
        try
        {
            Path path = Paths.get(fileName);
            raw = Files.readAllBytes(path);
            OutputStream out = Files.newOutputStream(path);
            String rawString = new String(raw, "ISO-8859-1");
            System.out.println(rawString);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(raw);
            String cipherString = new String(cipherText, "ISO-8859-1");
            System.out.println(cipherString);
            out.write(cipherText);
            out.close();
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void decrypt()
    {
     
        byte[] cipherText = null;
        byte[] plainText = null;
        
        try
        {
            Path path = Paths.get(fileName);
            cipherText = Files.readAllBytes(path);
            OutputStream out = Files.newOutputStream(path);
            String rawString = new String(cipherText, "ISO-8859-1");
            System.out.println(rawString);
            cipher.init(Cipher.DECRYPT_MODE, key);
            plainText = cipher.doFinal(cipherText);
            String plainString = new String(plainText, "ISO-8859-1");
            System.out.println(plainString);
            out.write(plainText);
            out.close();
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
