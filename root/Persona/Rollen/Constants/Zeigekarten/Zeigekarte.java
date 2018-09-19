package root.Persona.Rollen.Constants.Zeigekarten;

public class Zeigekarte {
    public String name;
    public String title;
    public String imagePath;

    public boolean equals(Zeigekarte zeigekarte) {
        return name.equals(zeigekarte.name);
    }
}
