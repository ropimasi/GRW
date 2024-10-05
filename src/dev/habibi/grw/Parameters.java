package dev.habibi.grw;

public class Parameters {

	static final int TEMPO_MINIMO = 5;
	static final int TEMPO_PADRAO = 80;
	static final String DIRETORIO_PADRAO_IMAGENS = System.getProperty("user.home") + "/my-wallpapers";
	static final boolean RODANDO_PADRAO = false;

	static final String CAMIN_PAD_DIR_HOME_CONFIG = System.getProperty("user.home") + "/.config";
	static final String CAMIN_PAD_DIR_CONFIG_GRW = CAMIN_PAD_DIR_HOME_CONFIG + "/grw";
	static final String CAMIN_PAD_ARQ_CONFIG_GRW_YAML = CAMIN_PAD_DIR_HOME_CONFIG + "/grw.yaml";

	static final String PROJECT_VERSION_MAJOR = "0";
	static final String PROJECT_VERSION_MINOR = "6";
	static final String PROJECT_VERSION_PATCH = "0";
	static final String PROJECT_VERSION_DESCR = "snapshot";
	static final String PROJECT_VERSION = PROJECT_VERSION_MAJOR + "." + PROJECT_VERSION_MINOR + "."
			+ PROJECT_VERSION_PATCH + "-" + PROJECT_VERSION_DESCR;
	static final String PROJECT_NAME = "Gnome Randomic Wallpaper";
	static final String PROJECT_ALIAS = "grw";

}
