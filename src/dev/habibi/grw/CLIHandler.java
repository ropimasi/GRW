package dev.habibi.grw;

import java.io.File;



public class CLIHandler {

	public static void processarArgumentos(String[] args) {
		if (args.length == 0 || args[0].equals("-h")) {
			DialogUI.mostrarAjuda();
			return;
		}

		ConfigManager.criarDirArqConfigGrwSeNaoExiste();

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-t":
				if (i + 1 < args.length) {
					definirTempo(args[++i]);
				} else {
					System.out.println("Erro: A opção -t requer um valor de tempo.");
					return;
				}
				break;

			case "-d":
				if (i + 1 < args.length) {
					definirDiretorioImagens(args[++i]);

				} else {
					System.out.println("Erro: A opção -d requer um diretório de imagens.");
					return;
				}
				break;

			case "-s":
				DialogUI.mostrarStatus();
				break;

			case "-v":
				DialogUI.mostrarVersao();
				break;

			case "-a":
				WallpaperManager.iniciarLoop();
				break;

			case "-o":
				WallpaperManager.pararLoop();
				break;

			case "-f":
				ConfigManager.definirConfiguracoesValoresPadroes();
				break;

			default:
				System.out.println("Opção desconhecida: " + args[i]);
				DialogUI.mostrarAjuda();
				break;
			}
		}
	}


	private static void definirDiretorioImagens(String diretorioImagens) {

		diretorioImagens = converterTilEmCaminho(diretorioImagens);

		File dir = new File(diretorioImagens);
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("Erro: Diretório de imagens inválido.");
		} else {
			ConfigManager.registrarConfigEmArquivo("diretorioImagens", diretorioImagens);
			System.out.println("O diretório de imagens foi definido [ " + diretorioImagens + " ].");
		}
	}


	/* Verifica e converte se o caminho iniciar com "~" substituindo pelo diretório home do usuário. */
	private static String converterTilEmCaminho(String diretorioImagens) {
		if (diretorioImagens.startsWith("~")) {
			diretorioImagens = System.getProperty("user.home") + diretorioImagens.substring(1);
		}
		return diretorioImagens;
	}


	private static void definirTempo(String tempo) {
		try {
			int tempoEspera = Integer.parseInt(tempo);
			if (tempoEspera < Parameters.TEMPO_MINIMO) {
				System.out.println(
						"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
								+ Parameters.TEMPO_MINIMO + ".");
			} else {
				ConfigManager.registrarConfigEmArquivo("tempoEspera", String.valueOf(tempoEspera));
				System.out.println("O tempo de espera foi definido para " + tempoEspera + " segundos.");
			}
		} catch (NumberFormatException e) {
			System.out.println(
					"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
							+ Parameters.TEMPO_MINIMO + ".");
		}
	}

}
