package dev.habibi.grw;

import java.io.File;



public class CLIHandler {

	public static void processarArgumentos(String[] args) {
		if (args.length == 0 || args[0].equals("-h")) {
			mostrarAjuda();
			return;
		}

		ConfigManager.criarArquivoConfigGrw();

		WallpaperManager wallpaperManager = new WallpaperManager(Parameters.TEMPO_PADRAO,
				Parameters.DIRETORIO_PADRAO_IMAGENS, Parameters.RODANDO_PADRAO);

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-t":
				if (i + 1 < args.length) {
					try {
						int tempoEspera = Integer.parseInt(args[++i]);
						if (tempoEspera < Parameters.TEMPO_MINIMO) {
							System.out.println(
									"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
											+ Parameters.TEMPO_MINIMO + ".");
						} else {

							System.out.println("O tempo de espera foi definido para " + tempoEspera + " segundos.");
							ConfigManager.registrarConfigEmArquivo("tempoEspera", String.valueOf(tempoEspera));
						}
					} catch (NumberFormatException e) {
						System.out.println(
								"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
										+ Parameters.TEMPO_MINIMO + ".");
					}
				} else {
					System.out.println("Erro: A opção -t requer um valor de tempo.");
					return;
				}
				break;

			case "-d":
				if (i + 1 < args.length) {
					String diretorioImagens = args[++i];

					/* Verifica se o caminho começa com "~" e substitui pelo diretório home do usuário */
					if (diretorioImagens.startsWith("~")) {
						diretorioImagens = System.getProperty("user.home") + diretorioImagens.substring(1);
					}

					File dir = new File(diretorioImagens);
					if (!dir.exists() || !dir.isDirectory()) {
						System.out.println("Erro: Diretório de imagens inválido.");
					} else {
						System.out.println("O diretório de imagens foi definido [ " + diretorioImagens + " ].");
						ConfigManager.registrarConfigEmArquivo("diretorioImagens", diretorioImagens);
					}
				} else {
					System.out.println("Erro: A opção -d requer um diretório de imagens.");
					return;
				}
				break;

			case "-a":
				wallpaperManager.iniciarLoop();
				break;

			case "-o":
				wallpaperManager.pararLoop();
				break;

			case "-s":
				ConfigManager.mostrarStatus();
				break;

			default:
				System.out.println("Opção desconhecida: " + args[i]);
				mostrarAjuda();
				break;
			}
		}
	}


	private static void mostrarAjuda() {
		System.out.println("Uso: grw [opções]");
		System.out.println("Opções:");
		System.out.println("\t-t <tempo> \t\t Definir o intervalo de tempo entre trocas de papel de parede.");
		System.out.println("\t-d <diretório> \t\t Definir o diretório de imagens.");
		System.out.println("\t-a \t\t\t Iniciar a troca automática de papel de parede.");
		System.out.println("\t-o \t\t\t Parar a troca automática de papel de parede.");
		System.out.println("\t-s \t\t\t Mostrar o status atual.");
		System.out.println("\t-h \t\t\t Mostrar esta ajuda.");
	}
}
