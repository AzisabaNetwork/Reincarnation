package net.azisaba.rc.ui;

public class CLI
{
    public static final String SEPARATOR = "===================================================";

    public static final String SHORT_SEPARATOR = "-----------------------";

    public static String getSpaces(int length)
    {
        return " ".repeat(Math.max(0, length));
    }
}
