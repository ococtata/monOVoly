package utility;

import config.GeneralConfig;

public class TextUtil implements Scanner{
	public synchronized static void pressEnter() {
		System.out.print(" Press ENTER to continue...");
		if (scan.hasNextLine()) {
            scan.nextLine();
        }
	}
	
	public static void printHorizontalBorder(int len) {
		System.out.print(" ");
		for(int i = 0; i < len; i++) {
			System.out.print(GeneralConfig.HORIZONTAL);
		}
		System.out.println();
	}
	
	public static void clearScreen() {
		for(int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
	
	public static void printHeader(String headerText, int width, int height) {
		String title = " " + headerText + " ";
        int padding = (width - title.length()) / 2;
        
        System.out.println();
        System.out.print(" ");
        System.out.print(GeneralConfig.TOP_LEFT);
        for (int i = 0; i < width - 2; i++) {
            System.out.print(GeneralConfig.HORIZONTAL);
        }
        System.out.println(GeneralConfig.TOP_RIGHT);
        
        System.out.print(" ");
        System.out.print(GeneralConfig.VERTICAL);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.print(title);
        for (int i = 0; i < width - padding - title.length() - 2; i++) {
            System.out.print(" ");
        }
        System.out.println(GeneralConfig.VERTICAL);
        
        System.out.print(" ");
        System.out.print(GeneralConfig.BOTTOM_LEFT);
        for (int i = 0; i < width - 2; i++) {
            System.out.print(GeneralConfig.HORIZONTAL);
        }
        System.out.println(GeneralConfig.BOTTOM_RIGHT);
	}
}
