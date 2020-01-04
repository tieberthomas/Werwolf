package root.Logic.Persona.Rollen.Bonusrollen;

import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Tarnumhang extends Bonusrolle {
    public static final String ID = "ID_Tarnumhang";
    public static final String NAME = "Tarnumhang";
    public static final String IMAGE_PATH = ImagePath.TARNUMHANG_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public static final Color COLOR = Color.BLACK;

    public boolean schutzAktiv = true;

    public Tarnumhang() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
        this.color = COLOR;
    }

    @Override
    public Zeigekarte isTötendInfo() {
        return new Tarnumhang_BonusrollenType();
    }

    @Override
    public Zeigekarte getFraktionInfo() {
        return new Tarnumhang_BonusrollenType();
    }

    @Override
    public BonusrollenType getBonusrollenTypeInfo() {
        return new Tarnumhang_BonusrollenType();
    }

    public void consumeSchutz() {
        schutzAktiv = false;
    }
}
