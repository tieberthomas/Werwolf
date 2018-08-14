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
                Spieler spieler = new Spieler(line);
                Spieler.game.spieler.add(spieler);
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

    public boolean readComposition(String filePath){
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            line = br.readLine();
            int numberOfPlayers = Integer.parseInt(line);

            for(int i=0; i<numberOfPlayers; i++) {
                line = br.readLine();
                String[] fractals = line.split(" ");
                /*Spieler spieler = new Spieler(fractals[0].replace("*"," "));

                Hauptrolle hauptrolle = Hauptrolle.findHauptrolle(fractals[1].replace("*"," "));;
                spieler.hauptrolle = hauptrolle;
                if(hauptrolle!=null) {
                    Hauptrolle.mainRolesInGame.add(hauptrolle);
                }

                Nebenrolle nebenrolle = Nebenrolle.findNebenrolle(fractals[2].replace("*"," "));;
                spieler.nebenrolle = nebenrolle;
                if(nebenrolle!=null) {
                    Nebenrolle.secondaryRolesInGame.add(nebenrolle);
                }*/
                String name = fractals[0].replace("*"," ");
                String hauptrolleString = fractals[1].replace("*"," ");
                String nebenrolleString = fractals[2].replace("*"," ");

                Spieler spieler = new Spieler(name, hauptrolleString, nebenrolleString);

                Hauptrolle hauptrolle = spieler.hauptrolle;
                if(hauptrolle!=null) {
                    Hauptrolle.mainRolesInGame.add(hauptrolle);
                }

                Nebenrolle nebenrolle = spieler.nebenrolle;
                if(nebenrolle!=null) {
                    Nebenrolle.secondaryRolesInGame.add(nebenrolle);
                }
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

    public boolean writeComposition (String filePath, ArrayList<Spieler> spieler, ArrayList<String> mainRolesLeft, ArrayList<String> secondaryRolesLeft) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

            ArrayList<String> compositionStrings = new ArrayList<>();

            for(Spieler currentSpieler : spieler) {
                compositionStrings.add(buildCompositionString(currentSpieler));
            }

            writeArrayList(writer, compositionStrings);
            writeArrayList(writer, mainRolesLeft);
            writeArrayList(writer, secondaryRolesLeft);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Something went wrong while writing the file");
            return false;
        }

        return true;
    }

    public String buildCompositionString(Spieler spieler) {
        String name = spieler.name.replace(" ","*");
        String hauptrolle = spieler.hauptrolle.getName().replace(" ","*");
        String nebenrolle = spieler.nebenrolle.getName().replace(" ","*");
        return name + " " + hauptrolle + " " + nebenrolle;
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
