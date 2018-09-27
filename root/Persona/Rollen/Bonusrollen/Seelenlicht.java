package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Seelenlicht extends Bonusrolle {
    public static final String NAME = "Seelenlicht";
    public static final String IMAGE_PATH = ImagePath.SEELENLICHT_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public Seelenlicht() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
    }

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            spieler.bonusrolle = bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(NAME);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
                bonusrolle = new ReineSeele();
            } else {
                bonusrolle = new SchwarzeSeele();
            }

            return bonusrolle;
        } else {
            return new ReineSeele();
        }
    }
}
