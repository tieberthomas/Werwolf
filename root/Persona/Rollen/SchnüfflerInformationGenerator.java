package root.Persona.Rollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.InformationType;
import root.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Persona.Rollen.Constants.Zeigekarten.SpäherZeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Spieler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SchnüfflerInformationGenerator {
    private Spieler player;
    private Random random = new Random();
    private ArrayList<InformationType> erhaltbareInformationen = new ArrayList<>(Arrays.asList(
            InformationType.FRAKTION, InformationType.TÖTEND, InformationType.NEBENROLLENTYPE));
    private ArrayList<BonusrollenType> bonusrollenTypes = new ArrayList<>(Arrays.asList(
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
        BonusrollenType bonusrollenType = null;

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
                    bonusrollenType = generateNebenrollenInformation(isCorrectInformation);
                    break;
            }
        }

        return new SchnüfflerInformation(player.name, fraktion, tötend, bonusrollenType);
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

    private BonusrollenType generateNebenrollenInformation(boolean correctInformation) {
        BonusrollenType playerBonusrollenType = player.bonusrolle.type;
        if (correctInformation) {
            return playerBonusrollenType;
        }

        removeNebenrollenType(bonusrollenTypes, playerBonusrollenType);

        int numberOfNebenrollenTypes = bonusrollenTypes.size();
        int decision = random.nextInt(numberOfNebenrollenTypes);

        return bonusrollenTypes.get(decision);
    }

    private void removeNebenrollenType(ArrayList<BonusrollenType> bonusrollenTypes, BonusrollenType bonusrollenTypeToRemove) {
        BonusrollenType bonusrollenTypeInListToRemove = null;
        for (BonusrollenType bonusrollenType : bonusrollenTypes) {
            if (bonusrollenType.equals(bonusrollenTypeToRemove)) {
                bonusrollenTypeInListToRemove = bonusrollenType;
            }
        }

        bonusrollenTypes.remove(bonusrollenTypeInListToRemove);
    }
}

