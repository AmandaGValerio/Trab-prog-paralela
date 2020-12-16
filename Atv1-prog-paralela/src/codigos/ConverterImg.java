package codigos;

import java.awt.image.BufferedImage;

public class ConverterImg extends Thread{
	private int altura;
	private int largura;
	private BufferedImage imagem;

	//conseguir fazer o retorno das partes da imagem
	public ConverterImg(int altura, int largura, BufferedImage imagem) {
		this.altura = altura;
		this.largura = largura;
		this.imagem = imagem;
	}
	@Override
	public void run() {
		BufferedImage processada = null;
		for (int y = 0; y < this.altura; y++) {
            for (int x = 0; x < this.largura; x++) {
                int colorido = this.imagem.getRGB(x, y);
                int cinza = escalaDeCinza(colorido);
				processada.setRGB(x, y, cinza);
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
