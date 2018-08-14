package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.Objects;

/**
 * Created by Steve on 12.11.2017.
 */
public class Analytiker extends Nebenrolle
{
    public static final String name = "Analytiker";
    public static final String imagePath = ResourcePath.ANALYTIKER_KARTE;
    public static boolean spammable = true;
    public NebenrollenTyp type = NebenrollenTyp.INFORMATIV;
    public static final String GLEICH = "gleich";
    public static final String UNGLEICH = "ungleich";
    public Spieler besuchtAnalysieren = null;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenTyp getType() { return type; }

    public boolean showTarnumhang(Spieler spieler1, Spieler spieler2){
        if(spieler1.nebenrolle.getName().equals(Tarnumhang.name) ||
                spieler2.nebenrolle.getName().equals(Tarnumhang.name)) {
            return true;
        }

        boolean bestienmeisterBeiSpielern = false;
        boolean analytikerKeinBürger = false;

        if(spieler1.hauptrolle.getName().equals(Bestienmeister.name) ||
                spieler2.hauptrolle.getName().equals(Bestienmeister.name)) {
            bestienmeisterBeiSpielern = true;
        }

        Spieler analytikerSpieler = game.findSpielerPerRolle(name);

        if(!analytikerSpieler.hauptrolle.getFraktion().getName().equals(Bürger.name)){
            analytikerKeinBürger = true;
        }

        return bestienmeisterBeiSpielern && analytikerKeinBürger;
    }

    public String analysiere(Spieler spieler1, Spieler spieler2) {
        besucht = spieler1;
        besuchtAnalysieren = spieler2;
        String name1 = spieler1.name;
        String name2 = spieler2.name;

        String fraktion1 = spieler1.hauptrolle.getFraktion().getName();
        String fraktion2 = spieler2.hauptrolle.getFraktion().getName();

        if (Objects.equals(fraktion1, fraktion2)) {
            return GLEICH;
        } else {
            Liebespaar liebespaar = game.liebespaar;
            if (liebespaar!= null && liebespaar.spieler1!=null) {
                String liebespartner1 = liebespaar.spieler1.name;
                String liebespartner2 = liebespaar.spieler2.name;

                if (Objects.equals(name1, liebespartner1) && Objects.equals(name2, liebespartner2) ||
                        Objects.equals(name2, liebespartner1) && Objects.equals(name1, liebespartner2)) {
                    return GLEICH;
                } else {
                    return UNGLEICH;
                }
            } else {
                return UNGLEICH;
            }
        }
    }
}
