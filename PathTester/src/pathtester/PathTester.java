package pathtester;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author valentyne
 */
public class PathTester {
    public static void main(String[] args) {
        Path path = Paths.get("..\\");
        
        if (Files.exists(path)){
            print("Exists");
            
            if(Files.isDirectory(path)){
                print("It is a directory");
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);){
                    for(Path p:directoryStream){
                        print(p);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PathTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            print("Does not exist");
        }
        
        System.out.println(path.toAbsolutePath());
    }
    
    
    public static <T> void print( T str) {
        System.out.println(str);
    }
}