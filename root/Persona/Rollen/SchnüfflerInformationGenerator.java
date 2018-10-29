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
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Spieler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SchnüfflerInformationGenerator {
    private Spieler spieler;
    private Random random = new Random();
    private ArrayList<InformationType> erhaltbareInformationen = new ArrayList<>(Arrays.asList(
            InformationType.FRAKTION, InformationType.TÖTEND, InformationType.BONUSROLLEN_TYPE));
    private ArrayList<BonusrollenType> bonusrollenTypes = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));

    public SchnüfflerInformationGenerator(Spieler spieler) {
        this.spieler = spieler;
    }

    public SchnüfflerInformation generateInformation() {
        Bonusrolle schnüffler = Schnüffler.game.findSpielerPerRolle(Schnüffler.NAME).bonusrolle;
        if (schnüffler.showTarnumhang(schnüffler, spieler)) {
            return new SchnüfflerInformation(spieler.name);
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
                case BONUSROLLEN_TYPE:
                    isCorrectInformation = correctInformation.equals(InformationType.BONUSROLLEN_TYPE);
                    bonusrollenType = generateBonusrollenInformation(isCorrectInformation);
                    break;
            }
        }

        return new SchnüfflerInformation(spieler.name, fraktion, tötend, bonusrollenType);
    }

    private boolean spielerIsSchamanin() {
        return spieler.hauptrolle.name.equals(Schamanin.NAME);
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
                return InformationType.BONUSROLLEN_TYPE;

        }

        return InformationType.BONUSROLLEN_TYPE;
    }

    private Fraktion generateFraktionInformation(boolean correctInformation) {
        Fraktion spielerFraktion = spieler.hauptrolle.fraktion;
        if(spieler.bonusrolle.equals(Henker.NAME)) {
            spielerFraktion = new Bürger();
        }

        if (correctInformation) {
            return spielerFraktion;
        }

        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        removeFraktion(fraktionen, spielerFraktion);

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
        boolean isKilling = spieler.hauptrolle.killing;

        if (!correctInformation) { //TODO geht mit XOR ??
            isKilling = !isKilling;
        }

        return SpäherZeigekarte.getZeigekarte(isKilling);
    }

    private BonusrollenType generateBonusrollenInformation(boolean correctInformation) {
        BonusrollenType spielerBonusrollenType = spieler.bonusrolle.type;
        if (correctInformation) {
            return spielerBonusrollenType;
        }

        removeBonusrollenType(bonusrollenTypes, spielerBonusrollenType);

        int numberOfBonusrollenTypes = bonusrollenTypes.size();
        int decision = random.nextInt(numberOfBonusrollenTypes);

        return bonusrollenTypes.get(decision);
    }

    private void removeBonusrollenType(ArrayList<BonusrollenType> bonusrollenTypes, BonusrollenType bonusrollenTypeToRemove) {
        BonusrollenType bonusrollenTypeInListToRemove = null;
        for (BonusrollenType bonusrollenType : bonusrollenTypes) {
            if (bonusrollenType.equals(bonusrollenTypeToRemove)) {
                bonusrollenTypeInListToRemove = bonusrollenType;
            }
        }

        bonusrollenTypes.remove(bonusrollenTypeInListToRemove);
    }
}

