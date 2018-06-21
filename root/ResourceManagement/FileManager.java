package root.ResourceManagement;

import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Spieler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileManager {
    public boolean createFolderInAppdata() {
        boolean result = false;
        File theDir = new File(ResourcePath.SAVE_FILE_PATH);

        if (!theDir.exists()) {
            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                System.out.println("Programm does not have the permisson to create a Folder in Appdata.");
            }
        } else {
            result = true;
        }

        return result;
    }

    public boolean read(String filePath){
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            line = br.readLine();
            int numberOfPlayers = Integer.parseInt(line);

            for(int i=0; i<numberOfPlayers; i++) {
                line = br.readLine();
                Spieler.spieler.add(new Spieler(line));
            }

            line = br.readLine();
            int numberOfMainRoles = Integer.parseInt(line);

            for(int i=0; i<numberOfMainRoles; i++) {
                line = br.readLine();
                Hauptrolle hauptrolle = Hauptrolle.findHauptrolle(line);
                if(hauptrolle!=null) {
                    Hauptrolle.mainRolesInGame.add(hauptrolle);
                }

            }

            line = br.readLine();
            int numberOfSecondaryRoles = Integer.parseInt(line);

            for(int i=0; i<numberOfSecondaryRoles; i++) {
                line = br.readLine();
                Nebenrolle nebenrolle = Nebenrolle.findNebenrolle(line);
                if(nebenrolle!=null) {
                    Nebenrolle.secondaryRolesInGame.add(nebenrolle);
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file");
            return false;
        }

        return true;
    }

    public boolean write (String filePath, ArrayList<String> spieler, ArrayList<String> hauptrollen, ArrayList<String> nebenrollen) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

            writeArrayList(writer, spieler);
            writeArrayList(writer, hauptrollen);
            writeArrayList(writer, nebenrollen);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while writing the file");
            return false;
        }

        return true;
    }

    public void writeArrayList(Writer writer, ArrayList<String> arrayToWrite) throws IOException{
        int numberOfLines = arrayToWrite.size();
        writer.write(Integer.toString(numberOfLines));
        writer.write("\n");
        for(String line : arrayToWrite) {
            writer.write(line);
            writer.write("\n");
        }
    }
}
