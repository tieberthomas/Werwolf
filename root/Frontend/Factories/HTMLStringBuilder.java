package root.Frontend.Factories;

import java.util.ArrayList;

public class HTMLStringBuilder {
    public static final String yellow = "yellow";
    public static final String gray = "#D3D3D3";
    public static final String blue = "#00FFFF";
    public static final String red = "red";
    public static final String white = "white";

    public static String buildHTMLText(ArrayList<String> texts, ArrayList<String> color) {
        String HTMLText = openHTML();
        StringBuilder stringBuilder = new StringBuilder();

        int i=0;

        for(String text : texts)
        {
            stringBuilder.append(buildColoredParagraph(text, color.get(i)));
            i++;
        }

        HTMLText += stringBuilder.toString();

        HTMLText += closeHTML();

        return HTMLText;
    }

    public static String buildHTMLText(String text) {
        String HTMLText = openHTML();

        HTMLText += openParagraph();

        HTMLText += text;

        HTMLText += closeParagraph();

        HTMLText += closeHTML();

        return HTMLText;
    }

    public static String buildColoredParagraph(String text, String color) {
        String HTMLText;

        HTMLText = openParagraphWithProperties(styleColorProperty(color))
                + text
                + closeParagraph();

        return HTMLText;
    }

    public static String openHTML()
    {
        return "<html>";
    }

    public static String closeHTML()
    {
        return "</html>";
    }

    public static String openParagraph()
    {
        return "<p>";
    }

    public static String openParagraphWithProperties(String properties)
    {
        return "<p " + properties + " >";
    }

    public static String closeParagraph()
    {
        return "</p>";
    }

    public static String styleColorProperty(String color)
    {
        return "style=\"background-color: " + color + ";\"";
    }
}
