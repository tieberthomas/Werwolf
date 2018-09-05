package root.Persona.Rollen.Constants.NebenrollenType;

public class NebenrollenType {
    public String name;
    public String title;
    public String imagePath;

    public boolean equals(NebenrollenType type) {
        return name.equals(type.name);
    }
}
