package codigos;

import java.awt.image.BufferedImage;

public class ConverterImg implements Runnable{
	private int alturai;
	private int alturaf;
	private int largurai;
	private int larguraf;
	private BufferedImage imagem;

	//conseguir fazer o retorno das partes da imagem
	public ConverterImg(int alturai, int alturaf, int largurai, int larguraf, BufferedImage imagem) {
		this.alturai = alturai;
		this.largurai = largurai;
		this.alturaf = alturaf;
		this.larguraf = larguraf;
		this.imagem = imagem;
	}
	@Override
	public void run() {
		BufferedImage processada = null;
		for (int y = alturai; y < this.alturaf; y++) {
            for (int x = largurai; x < this.larguraf; x++) {
                int colorido = this.imagem.getRGB(x, y);
                int cinza = escalaDeCinza(colorido);
				processada.setRGB(x, y, cinza);
				//conseguir retornar a imagem e juntar os pedaços
            }
        }
		
	}

	public int escalaDeCinza(int pixel) {
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
	    int r = (rgb >> 16) & 0xFF;
	    int g = (rgb >>  8) & 0xFF;
	    int b = (rgb >>  0) & 0xFF;
	    return new int[] { r, g, b };
	}

	public int integrar(int[] rgb) {
	    int r = (rgb[0] & 0xFF) << 16;
	    int g = (rgb[1] & 0xFF) <<  8;
	    int b = (rgb[2] & 0xFF) <<  0;
	    return r | g | b;
	}
	
}
