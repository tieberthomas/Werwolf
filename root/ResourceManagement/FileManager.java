package root.ResourceManagement;

import root.Spieler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileManager {
    public void createFolderInAppdata() {
        File theDir = new File(ResourcePath.SAVE_FILE_PATH);

        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
                System.out.println("Programm does not have the permisson to create a Folder in Appdata.");
            }
        }
    }

    public CompositionDto readComposition(String filePath){
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            CompositionDto compositionDto = new CompositionDto();
            compositionDto.spieler = readList(br);
            compositionDto.hauptrollen = readList(br);
            compositionDto.nebenrollen = readList(br);

            br.close();

            return compositionDto;
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file");
            return null;
        }
    }

    public boolean writeComposition(String filePath, ArrayList<String> spieler, ArrayList<String> hauptrollen, ArrayList<String> nebenrollen) {
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

    public GameDto readGame(String filePath){
        try {
            GameDto gameDto = new GameDto();
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;

            line = br.readLine();
            int numberOfPlayers = Integer.parseInt(line);

            for(int i=0; i<numberOfPlayers; i++) {
                line = br.readLine();
                String[] fractals = line.split(" ");

                String name = fractals[0].replace("*"," ");
                String hauptrolleString = fractals[1].replace("*"," ");
                String nebenrolleString = fractals[2].replace("*"," ");

                PlayerDto playerDto = new PlayerDto(name, hauptrolleString, nebenrolleString);
                gameDto.players.add(playerDto);
            }

            gameDto.compositionDto.hauptrollen = readList(br);
            gameDto.compositionDto.nebenrollen = readList(br);

            br.close();

            return gameDto;
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file");
            return null;
        }
    }

    public boolean writeGame(String filePath, ArrayList<Spieler> spieler, ArrayList<String> mainRolesLeft, ArrayList<String> secondaryRolesLeft) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

            ArrayList<String> compositionStrings = new ArrayList<>();

            for(Spieler currentSpieler : spieler) {
                compositionStrings.add(buildPlayerString(currentSpieler));
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

    public String buildPlayerString(Spieler spieler) {
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

    public ArrayList<String> readList(BufferedReader br) throws IOException {
        ArrayList<String> listStrings = new ArrayList<>();
        String line = br.readLine();
        int numberOfSecondaryRoles = Integer.parseInt(line);

        for (int i = 0; i < numberOfSecondaryRoles; i++) {
            line = br.readLine();
            listStrings.add(line);
        }

        return listStrings;
    }
}
