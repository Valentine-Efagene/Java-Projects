/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filehider;

import java.io.Serializable;

/**
 *
 * @author Edesiri .V. Efagene
 */
public class HiddenFile implements Serializable
{
    private String key;
    private String path;
    private boolean authenticated = false;
    
    public HiddenFile(String password, String pathOfFileToHide)
    {
        key = password;
        path = pathOfFileToHide;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getKey()
    {
        if(authenticated == true)
            return key;
        return null;
    }
    
    public boolean getAuthenticated()
    {
        return authenticated;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void authenticate(String pass)
    {
        if(pass.equals(key))
        {
            authenticated = true;
        }
    }
}
