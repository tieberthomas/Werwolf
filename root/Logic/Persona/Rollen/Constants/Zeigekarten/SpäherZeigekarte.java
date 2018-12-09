package root.Logic.Persona.Rollen.Constants.Zeigekarten;

public class SpäherZeigekarte extends Zeigekarte {
    public static SpäherZeigekarte getZeigekarte(boolean killing) {
        if (killing) {
            return new Tötend();
        } else {
            return new Nicht_Tötend();
        }
    }
}
