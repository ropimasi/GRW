package dev.ropimasi.grw;

public class Utility {

	/* Verify and convert, if the path starts with '~' replacing it with the user's home directory. */
	public static String convertTildeToPath(String imagesDirectory) {
		if (imagesDirectory.startsWith("~")) {
			imagesDirectory = System.getProperty("user.home") + imagesDirectory.substring(1);
		}
		return imagesDirectory;
	}

}
