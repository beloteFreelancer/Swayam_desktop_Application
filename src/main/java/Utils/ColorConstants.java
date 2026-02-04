package Utils;

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * Standard color constants for consistent GUI theming across the application
 */
public class ColorConstants {

    /**
     * Safely loads an icon from the classpath resources
     * 
     * @param resourcePath The path to the icon resource (e.g., "/icons/icon.png")
     * @return ImageIcon if resource exists, null otherwise
     */
    public static ImageIcon loadIcon(String resourcePath) {
        try {
            URL resourceUrl = ColorConstants.class.getResource(resourcePath);
            if (resourceUrl != null) {
                return new ImageIcon(resourceUrl);
            }
        } catch (Exception e) {
            // Log the error but don't crash the application
            System.err.println("Failed to load icon: " + resourcePath + " - " + e.getMessage());
        }
        return null;
    }

    // Primary Colors
    public static final java.awt.Color PRIMARY_BLUE = new java.awt.Color(0, 102, 255);
    public static final java.awt.Color PRIMARY_BLUE_LIGHT = new java.awt.Color(51, 153, 255);
    public static final java.awt.Color PRIMARY_BLUE_DARK = new java.awt.Color(0, 51, 153);

    // Secondary Colors
    public static final java.awt.Color SECONDARY_ORANGE = new java.awt.Color(255, 102, 0);
    public static final java.awt.Color SECONDARY_ORANGE_LIGHT = new java.awt.Color(255, 153, 51);
    public static final java.awt.Color SECONDARY_ORANGE_DARK = new java.awt.Color(204, 82, 0);

    // Status Colors
    public static final java.awt.Color SUCCESS_GREEN = new java.awt.Color(34, 139, 34);
    public static final java.awt.Color WARNING_YELLOW = new java.awt.Color(255, 193, 7);
    public static final java.awt.Color ERROR_RED = new java.awt.Color(220, 53, 69);
    public static final java.awt.Color INFO_BLUE = new java.awt.Color(23, 162, 184);

    // Text Colors
    public static final java.awt.Color TEXT_PRIMARY = new java.awt.Color(33, 37, 41);
    public static final java.awt.Color TEXT_SECONDARY = new java.awt.Color(108, 117, 125);
    public static final java.awt.Color TEXT_LIGHT = new java.awt.Color(255, 255, 255);
    public static final java.awt.Color TEXT_MUTED = new java.awt.Color(173, 181, 189);

    // Background Colors
    public static final java.awt.Color BACKGROUND_LIGHT = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color BACKGROUND_MEDIUM = new java.awt.Color(233, 236, 239);
    public static final java.awt.Color BACKGROUND_DARK = new java.awt.Color(52, 58, 64);

    // Border Colors
    public static final java.awt.Color BORDER_LIGHT = new java.awt.Color(222, 226, 230);
    public static final java.awt.Color BORDER_MEDIUM = new java.awt.Color(173, 181, 189);
    public static final java.awt.Color BORDER_DARK = new java.awt.Color(108, 117, 125);

    // Input Field Colors
    public static final java.awt.Color INPUT_BACKGROUND = new java.awt.Color(255, 255, 255);
    public static final java.awt.Color INPUT_BACKGROUND_FOCUS = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color INPUT_BORDER = new java.awt.Color(206, 212, 218);
    public static final java.awt.Color INPUT_BORDER_FOCUS = PRIMARY_BLUE;

    // Button Colors
    public static final java.awt.Color BUTTON_PRIMARY = PRIMARY_BLUE;
    public static final java.awt.Color BUTTON_SECONDARY = new java.awt.Color(108, 117, 125);
    public static final java.awt.Color BUTTON_SUCCESS = SUCCESS_GREEN;
    public static final java.awt.Color BUTTON_DANGER = ERROR_RED;
    public static final java.awt.Color BUTTON_WARNING = WARNING_YELLOW;

    // Table Colors
    public static final java.awt.Color TABLE_HEADER_BACKGROUND = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color TABLE_ROW_ALTERNATE = new java.awt.Color(248, 249, 250);
    public static final java.awt.Color TABLE_SELECTION = new java.awt.Color(0, 123, 255, 25);

    // Panel Colors
    public static final java.awt.Color PANEL_BACKGROUND = new java.awt.Color(255, 255, 255);
    public static final java.awt.Color PANEL_HEADER_BACKGROUND = PRIMARY_BLUE;
    public static final java.awt.Color PANEL_BORDER = BORDER_LIGHT;

    // Legacy Colors (for backward compatibility during migration)
    public static final java.awt.Color LEGACY_BLUE = new java.awt.Color(0, 51, 255);
    public static final java.awt.Color LEGACY_LIGHT_BG = new java.awt.Color(242, 231, 231);
    public static final java.awt.Color LEGACY_GRAY_TEXT = new java.awt.Color(153, 153, 153);
    public static final java.awt.Color LEGACY_BUTTON_TEXT = new java.awt.Color(204, 204, 204);
}
