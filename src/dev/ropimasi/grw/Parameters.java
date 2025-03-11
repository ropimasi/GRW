package dev.ropimasi.grw;

public class Parameters {

	static final int TIME_MIN = 5;
	static final int TIME_DEFAULT = 80;
	static final String IMAGES_DIR_DEFAULT = System.getProperty("user.home") + "/my-wallpapers";
	static final boolean RUNNING_DEFAULT = false;

	static final String PATH_HOME_CONFIG_DEFAULT = System.getProperty("user.home") + "/.config";
	static final String PATH_CONFIG_GRW_DEFAULT = PATH_HOME_CONFIG_DEFAULT + "/grw";
	static final String PATH_CONFIG_GRW_FILE_YAML_DEFAULT = PATH_CONFIG_GRW_DEFAULT + "/grw.yaml";

	static final String PROJECT_VERSION_MAJOR = "0";
	static final String PROJECT_VERSION_MINOR = "6";
	static final String PROJECT_VERSION_PATCH = "2";
	static final String PROJECT_VERSION_DESCR = "alpha";
	static final String PROJECT_VERSION = PROJECT_VERSION_MAJOR + "." + PROJECT_VERSION_MINOR + "."
			+ PROJECT_VERSION_PATCH + "-" + PROJECT_VERSION_DESCR;
	static final String PROJECT_NAME = "Gnome Randomic Wallpaper";
	static final String PROJECT_ALIAS = "grw";

}
