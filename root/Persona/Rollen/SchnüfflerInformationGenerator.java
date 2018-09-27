package root.Persona.Rollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.InformationType;
import root.Persona.Rollen.Constants.NebenrollenType.Aktiv;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Persona.Rollen.Constants.Zeigekarten.SpäherZeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Persona.Rollen.Nebenrollen.Schnüffler;
import root.Spieler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SchnüfflerInformationGenerator {
    private Spieler player;
    private Random random = new Random();
    private ArrayList<InformationType> erhaltbareInformationen = new ArrayList<>(Arrays.asList(
            InformationType.FRAKTION, InformationType.TÖTEND, InformationType.NEBENROLLENTYPE));
    private ArrayList<NebenrollenType> nebenrollenTypes = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));

    public SchnüfflerInformationGenerator(Spieler player) {
        this.player = player;
    }

    public SchnüfflerInformation generateInformation() {
        Bonusrolle schnüffler = Schnüffler.game.findSpielerPerRolle(Schnüffler.NAME).bonusrolle;
        if (schnüffler.showTarnumhang(schnüffler, player)) {
            return new SchnüfflerInformation(player.name);
        }

        InformationType correctInformation = decideCorrectInformation();
        Fraktion fraktion = null;
        SpäherZeigekarte tötend = null;
        NebenrollenType nebenrollenType = null;

        boolean isCorrectInformation;

        for (InformationType type : erhaltbareInformationen) {
            switch (type) {
                case FRAKTION:
                    isCorrectInformation = correctInformation.equals(InformationType.FRAKTION);
                    fraktion = generateFraktionInformation(isCorrectInformation);
                    break;
                case TÖTEND:
                    isCorrectInformation = correctInformation.equals(InformationType.TÖTEND);
                    tötend = generateTötendInformation(isCorrectInformation);
                    break;
                case NEBENROLLENTYPE:
                    isCorrectInformation = correctInformation.equals(InformationType.NEBENROLLENTYPE);
                    nebenrollenType = generateNebenrollenInformation(isCorrectInformation);
                    break;
            }
        }

        return new SchnüfflerInformation(player.name, fraktion, tötend, nebenrollenType);
    }

    private boolean playerIsSchamanin() {
        return player.hauptrolle.name.equals(Schamanin.NAME);
    }

    private boolean schnüfflerIsNotBuerger() {
        Spieler spieler = Schnüffler.game.findSpielerPerRolle(Schnüffler.NAME);

        return !spieler.hauptrolle.fraktion.equals(new Bürger());
    }

    private InformationType decideCorrectInformation() {
        int numberOfInformations = 3;
        int decision = random.nextInt(numberOfInformations);

        switch (decision) {
            case 0:
                return InformationType.FRAKTION;
            case 1:
                return InformationType.TÖTEND;
            case 2:
                return InformationType.NEBENROLLENTYPE;

        }

        return InformationType.NEBENROLLENTYPE;
    }

    private Fraktion generateFraktionInformation(boolean correctInformation) {
        Fraktion playerFraktion = player.hauptrolle.fraktion;
        if (correctInformation) {
            return playerFraktion;
        }

        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        removeFraktion(fraktionen, playerFraktion);

        int numberOfFraktionen = fraktionen.size();
        int decision = random.nextInt(numberOfFraktionen);

        return fraktionen.get(decision);
    }

    private void removeFraktion(ArrayList<Fraktion> fraktionen, Fraktion fraktionToRemove) {
        Fraktion fraktionInListToRemove = null;
        for (Fraktion fraktion : fraktionen) {
            if (fraktion.equals(fraktionToRemove)) {
                fraktionInListToRemove = fraktion;
            }
        }

        fraktionen.remove(fraktionInListToRemove);
    }

    private SpäherZeigekarte generateTötendInformation(boolean correctInformation) {
        boolean isKilling = player.hauptrolle.killing;

        if (!correctInformation) {
            isKilling = !isKilling;
        }

        return SpäherZeigekarte.getZeigekarte(isKilling);
    }

    private NebenrollenType generateNebenrollenInformation(boolean correctInformation) {
        NebenrollenType playerNebenrollenType = player.bonusrolle.type;
        if (correctInformation) {
            return playerNebenrollenType;
        }

        removeNebenrollenType(nebenrollenTypes, playerNebenrollenType);

        int numberOfNebenrollenTypes = nebenrollenTypes.size();
        int decision = random.nextInt(numberOfNebenrollenTypes);

        return nebenrollenTypes.get(decision);
    }

    private void removeNebenrollenType(ArrayList<NebenrollenType> nebenrollenTypes, NebenrollenType nebenrollenTypeToRemove) {
        NebenrollenType nebenrollenTypeInListToRemove = null;
        for (NebenrollenType nebenrollenType : nebenrollenTypes) {
            if (nebenrollenType.equals(nebenrollenTypeToRemove)) {
                nebenrollenTypeInListToRemove = nebenrollenType;
            }
        }

        nebenrollenTypes.remove(nebenrollenTypeInListToRemove);
    }
}

