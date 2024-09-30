package dev.habibi.grw;

import java.io.File;
import java.util.Random;



public class WallpaperManager {

	public WallpaperManager(int tempoEspera, String diretorioImagens, boolean rodando) {
		ConfigManager.tempoEspera = tempoEspera;
		ConfigManager.diretorioImagens = diretorioImagens;
		ConfigManager.rodando = rodando;
	}


	public void iniciarLoop() {
		ConfigManager.rodando = true;
		ConfigManager.registrarConfigEmArquivo("rodando", "true");

		while (ConfigManager.rodando) {
			ConfigManager.carregarConfiguracoesDoArquivo();

			System.out.println("Trocando papel de parede a cada " + ConfigManager.tempoEspera + " segundos, da origem "
					+ ConfigManager.diretorioImagens);

			trocarPapelDeParede();

			try {
				Thread.sleep(ConfigManager.tempoEspera * 1000L);
			} catch (InterruptedException e) {
				System.out.println("Erro no temporizador da thread: " + e.getMessage());
			}
		}
	}


	public void pararLoop() {
		ConfigManager.rodando = false;
		ConfigManager.registrarConfigEmArquivo("rodando", "false");
		System.out.println("Parando troca de papel de parede.");
	}


	private void trocarPapelDeParede() {
		File dir = new File(ConfigManager.diretorioImagens);
		File[] imagens = dir.listFiles((d, name) -> name.endsWith(".jpg") || name.endsWith(".png"));

		if (imagens == null || imagens.length == 0) {
			System.out.println("Nenhuma imagem encontrada no diretório.");
			return;
		}

		Random random = new Random();
		File imagemAleatoria = imagens[random.nextInt(imagens.length)];
		String caminhoImagem = imagemAleatoria.getAbsolutePath();

		try {
			String comando = "gsettings set org.gnome.desktop.background picture-uri-dark file://" + caminhoImagem;
			Runtime.getRuntime().exec(comando);
			System.out.println("Papel de parede alterado para: " + caminhoImagem);
			ConfigManager.registrarConfigEmArquivo("papelDeParede", caminhoImagem);
		} catch (Exception e) {
			System.out.println("Erro ao trocar o papel de parede: " + e.getMessage());
		}
	}

}
