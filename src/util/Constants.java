package util;

public class Constants {
	public static int SCREEN_WIDTH = 720;
	public static int SCREEN_HEIGHT = 720;
	public static String USERNAME = "root";
	public static String PASSWORD = "";
	public static String HOST = "localhost";
	public static String PORT = "3306";
	public static String DATABASE = "internet_clafes";
	public static String URL = String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, DATABASE);
}
