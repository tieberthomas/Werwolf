package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Rollen.Nebenrolle;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Steve on 12.11.2017.
 */
public class Analytiker extends Nebenrolle
{
    public static final String name = "Analytiker";
    public static final String imagePath = ResourcePath.ANALYTIKER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public String type = Nebenrolle.INFORMATIV;
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
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public String getType() { return type; }

    public boolean showTarnumhang(Spieler spieler1, Spieler spieler2){
        boolean tarnumhandBeiSpielern = false;

        if(spieler1.nebenrolle.getName().equals(Tarnumhang.name) ||
                spieler2.nebenrolle.getName().equals(Tarnumhang.name)) {
            tarnumhandBeiSpielern = true;
        }

        boolean bestienmeisterBeiSpielern = false;

        if(spieler1.hauptrolle.getName().equals(Bestienmeister.name) ||
                spieler2.hauptrolle.getName().equals(Bestienmeister.name)) {
            bestienmeisterBeiSpielern = true;
        }

        boolean analytikerKeinBürger = false;


        Spieler analytikerSpieler = Spieler.findSpielerPerRolle(name);
        if(analytikerSpieler!=null) {
            analytikerSpieler = Spieler.findSpielerPerRolle(Sammler.name);
        }
        if(!analytikerSpieler.hauptrolle.getFraktion().getName().equals(Bürger.name)){
            analytikerKeinBürger = true;
        }

        if(tarnumhandBeiSpielern || (bestienmeisterBeiSpielern && analytikerKeinBürger)) {
            return true;
        } else {
            return false;
        }
    }

    public String analysiere(Spieler spieler1, Spieler spieler2) {
        besucht = spieler1;
        besuchtAnalysieren = spieler2;
        String name1 = spieler1.name;
        String name2 = spieler2.name;

        String fraktion1 = spieler1.hauptrolle.getFraktion().getName();
        String fraktion2 = spieler2.hauptrolle.getFraktion().getName();

        ArrayList<String> answer = new ArrayList<>();

        if (Objects.equals(fraktion1, fraktion2)) {
            return GLEICH;
        } else {
            if (Liebespaar.spieler1 != null) {
                String liebespartner1 = Liebespaar.spieler1.name;
                String liebespartner2 = Liebespaar.spieler2.name;

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
