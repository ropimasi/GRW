package dev.habibi.grw;

public class DialogUI {
	
	/* FURTHER: UMA CLASSE PARA AS CONSTANTES DE MENSAGENS COM U.I. */
	static final String INF_ = "0";
	static final String WRN_ = "0";
	static final String ERR_ = "0";
	

	static void mostrarVersao() {
		System.out.println("\n");
		System.out.println(
				Parameters.PROJECT_NAME + " - " + Parameters.PROJECT_ALIAS + " versão " + Parameters.PROJECT_VERSION);
		System.out.println("\n");
	}


	static void mostrarAjuda() {
		System.out.println("\n");
		System.out.println(Parameters.PROJECT_NAME + " - " + Parameters.PROJECT_ALIAS);
		System.out.println("Syntaxe: grw [opções] [argumento]");
		System.out.println("Uso: grw [-h | -s | -t <tempo-segundos> | -d <caminho-diretório> | -a | -o]");
		System.out.println("Opções:");
		System.out.println("\t-h \t\t\t Mostrar esta ajuda.");
		System.out.println("\t-v \t\t\t Mostrar versão do grw.");
		System.out.println("\t-s \t\t\t Mostrar o status atual.");
		System.out.println("\t-t <tempo> \t\t Definir o intervalo de tempo entre trocas de papel de parede.");
		System.out.println("\t-d <diretório> \t\t Definir o diretório de imagens.");
		System.out.println("\t-a \t\t\t Iniciar a troca automática de papel de parede.");
		System.out.println("\t-o \t\t\t Parar a troca automática de papel de parede.");
		System.out.println("\n");
	}

	public static void mostrarStatus() {
		ConfigManager.carregarConfiguracoesDoArquivo();

		System.out.println("\n");
		System.out.println("Status atual:");
		System.out.println("\tTempo de espera: " + ConfigManager.tempoEspera + " segundos");
		System.out.println("\tDiretório de imagens: " + ConfigManager.diretorioImagens);
		System.out.println("\tRodando: " + ConfigManager.rodando);
		System.out.println("\n");
	}
}
