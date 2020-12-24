package codigos;

import java.awt.image.BufferedImage;

public class ConverterImg implements Runnable{
	
	private int alturai;
	private int alturaf;
	private int largurai;
	private int larguraf;
	private BufferedImage imagem;
	public BufferedImage processada; //retorna os pixeis cinza

	//conseguir fazer o retorno das partes da imagem
	public ConverterImg(int alturai, int alturaf, int largurai, int larguraf, BufferedImage imagem, BufferedImage processada) {
		this.alturai = alturai;
		this.largurai = largurai;
		this.alturaf = alturaf;
		this.larguraf = larguraf;
		this.imagem = imagem;
		this.processada = processada;
	}
	@Override
	public void run() {
		for (int a = alturai; a < this.alturaf; a++) {
            for (int l = largurai; l < this.larguraf; l++) {
                int colorido = this.imagem.getRGB(l, a);
                int cinza = calcular(colorido);
				processada.setRGB(l, a, cinza);
            }
        }
		
	}

	public int calcular(int pixel) {
	    int[] rgb = derivar(pixel);
	    int r = rgb[0];
	    int g = rgb[1];
	    int b = rgb[2];
	    int cinza = (r + g + b) / 3;
	    rgb[0] = cinza;
	    rgb[1] = cinza;
	    rgb[2] = cinza;
	    return integrar(rgb);
	}

	public int[] derivar(int rgb) {
		//metodo que separa o parametro em um vetor com o valor das cores
	    int r = (rgb >> 16) & 0xFF;
	    int g = (rgb >>  8) & 0xFF;
	    int b = (rgb >>  0) & 0xFF;
	    return new int[] { r, g, b };
	}

	public int integrar(int[] rgb) {
		//este método retorna os valores das cores operados em OU binário
	    int r = (rgb[0] & 0xFF) << 16;
	    int g = (rgb[1] & 0xFF) <<  8;
	    int b = (rgb[2] & 0xFF) <<  0;
	    return r | g | b;
	}
	
}
