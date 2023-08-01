package org.echo.chatformatting;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorsFormat {

    private Main plugin;

    public ColorsFormat() {
        plugin = Main.getInstance();
    }

    public String formatMessage(String message) {

        String colorsCode = plugin.getColorsConfig().getColorsCode();

        Pattern pattern = getCustomPattern(colorsCode);

        StringBuilder stringBuilder = new StringBuilder();

        Matcher matcher = pattern.matcher(message);

        int lastIndex = 0;

        // '&'
        while (matcher.find()) {

            int startIndex = matcher.start();
            int endIndex = matcher.end();

            // Before color code
            if (startIndex > lastIndex) {
                stringBuilder.append(message.substring(lastIndex, startIndex));
            }
            if (startIndex + 1 < message.length()) {

                // Get color code
                char colorChar = message.charAt(startIndex + 1);

                String colorCode = String.valueOf(colorChar);

                // Get Gradient
                List<String> hexGradient = plugin.getColorsConfig().getGradient(colorCode);

                // Gradient
                if (hexGradient != null) {
                    // Create Gradient
                    List<ChatColor> gradiant = Gradient.generateGradient(
                            hexGradient.get(0), hexGradient.get(1), endIndex - startIndex - 2
                    );

                    String gradiantPart = formatGradiant(gradiant, message.substring(startIndex + 2, endIndex));

                    stringBuilder.append(gradiantPart);
                }
                else {
                    // Custom colors
                    ChatColor color = plugin.getColorsConfig().getColor(colorCode);

                    if (color != null) {
                        stringBuilder.append(color)
                                . append(message.substring(startIndex + 2, endIndex));
                    }
                    else { // No colors
                        stringBuilder.append(message.substring(startIndex, endIndex));
                    }
                }
            } // No color character
            else {
                stringBuilder.append(message.substring(lastIndex, endIndex));
            }
            lastIndex = endIndex + 1;
        }

        // End of message
        if (lastIndex < message.length()) {
            stringBuilder.append(message.substring(lastIndex));
        }

        return stringBuilder.toString();
    }

    private Pattern getCustomPattern(String colorsCode) {
        // "&[c].*?(?=&[b]|$)"
        // '&c ... to ... &b or end' (for example)
        Pattern pattern = Pattern.compile(
                "&[" + colorsCode + "].*?(?=&[" + colorsCode + "]|$)");
        return pattern;
    }

    private String formatGradiant(List<ChatColor> gradiant, String part) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < part.length(); i++) {

            char c = part.charAt(i);
            stringBuilder.append("" + gradiant.get(i) + c);
        }
        return stringBuilder.toString();
    }
}
