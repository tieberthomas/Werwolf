package root.Logic.Persona.Rollen;

import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.InformationType;
import root.Logic.Persona.Rollen.Constants.InformationsCluster.TötendInfo;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.SpäherZeigekarte;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Henker.Henker;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Logic.Spieler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SchnüfflerInformationGenerator {
    private Spieler spieler;
    private Random random = new Random();
    private List<InformationType> erhaltbareInformationen = new ArrayList<>(Arrays.asList(
            InformationType.FRAKTION, InformationType.TÖTEND, InformationType.BONUSROLLEN_TYPE));
    private List<BonusrollenType> bonusrollenTypes = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));

    public SchnüfflerInformationGenerator(Spieler spieler) {
        this.spieler = spieler;
    }

    public SchnüfflerInformation generateInformation() {
        Bonusrolle schnüffler = Game.game.findSpielerPerRolle(Schnüffler.ID).bonusrolle;
        if (schnüffler.showTarnumhang(spieler)) {
            return new SchnüfflerInformation(spieler.name);
        }

        InformationType correctInformation = decideCorrectInformation();
        Fraktion fraktion = null;
        SpäherZeigekarte tötend = null;
        BonusrollenType bonusrollenType = null;

        boolean isCorrectInformation;

        for (InformationType type : erhaltbareInformationen) { //TODO generalize enum
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
        if (spieler.hauptrolle.equals(Henker.ID) || spieler.hauptrolle.equals(Wolfsmensch.ID)) {
            spielerFraktion = new Bürger();
        }

        if (correctInformation) {
            return spielerFraktion;
        }

        List<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        removeFraktion(fraktionen, spielerFraktion);

        int numberOfFraktionen = fraktionen.size();
        int decision = random.nextInt(numberOfFraktionen);

        return fraktionen.get(decision);
    }

    private void removeFraktion(List<Fraktion> fraktionen, Fraktion fraktionToRemove) {
        Fraktion fraktionInListToRemove = null;
        for (Fraktion fraktion : fraktionen) {
            if (fraktion.equals(fraktionToRemove)) {
                fraktionInListToRemove = fraktion;
            }
        }

        fraktionen.remove(fraktionInListToRemove);
    }

    private SpäherZeigekarte generateTötendInformation(boolean correctInformation) {
        Zeigekarte isKilling = SpäherZeigekarte.getZeigekarte(spieler.hauptrolle.killing);
        if (spieler.hauptrolle.equals(Henker.ID) || spieler.hauptrolle.equals(Wolfsmensch.ID)) {
            isKilling = spieler.hauptrolle.isTötendInfo(spieler);
        }

        if (!correctInformation) {
            isKilling = TötendInfo.getWrongInformation(isKilling);
        }

        return (SpäherZeigekarte) isKilling;
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

    private void removeBonusrollenType(List<BonusrollenType> bonusrollenTypes, BonusrollenType bonusrollenTypeToRemove) {
        BonusrollenType bonusrollenTypeInListToRemove = null;
        for (BonusrollenType bonusrollenType : bonusrollenTypes) {
            if (bonusrollenType.equals(bonusrollenTypeToRemove)) {
                bonusrollenTypeInListToRemove = bonusrollenType;
            }
        }

        bonusrollenTypes.remove(bonusrollenTypeInListToRemove);
    }
}

