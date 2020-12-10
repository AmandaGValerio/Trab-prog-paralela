//autores: Amanda, Lucas Alves

package codigos;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//iniciando o servidor
			ServerSocket servidor = new ServerSocket(8090);
			System.out.println("Servidor em execução na porta 8090...");
			System.out.println("Servidor aguardando requisições...");
			
			//esperando por uma conexao
			Socket cliente = servidor.accept();
			
			//se uma conexão for estabelecida
			System.out.println("Nova conexão estabelecida com o cliente " +
					cliente.getInetAddress().getHostAddress());
			
			// ler a imagem 
//			byte[] objectAsByte = new byte[cliente.getReceiveBufferSize()];
//	        BufferedInputStream bf = new BufferedInputStream(cliente.getInputStream());
//	        bf.read(objectAsByte);
//			ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
	        
//			esta linha serve apenas para testar o algoritmo		
			BufferedImage imagem = ImageIO.read(new File("imagem.jpg"));
			
			//dividindo a imagem
			int largura = imagem.getWidth();
	        int altura = imagem.getHeight();
	        BufferedImage processada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
	        
	        //dividir em 4 threads
	        
			//executar o algoritmo nas 4 threads ao mesmo tempo
			
			//algoritmo
	        for (int y = 0; y < altura; y++) {
	            for (int x = 0; x < largura; x++) {
	                int colorido = imagem.getRGB(x, y);
	                int cinza = escalaDeCinza(colorido);
	                processada.setRGB(x, y, cinza);
	            }
	        }

			//retornar a imagem em preto e branco
	        ImageIO.write(processada, "jpg", new File("pretobranco.jpg"));
			
			//fechando as conexoes
			servidor.close();
			cliente.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	
	public static int escalaDeCinza(int pixel) {
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

	public static int[] derivar(int rgb) {
	    int r = (rgb >> 16) & 0xFF;
	    int g = (rgb >>  8) & 0xFF;
	    int b = (rgb >>  0) & 0xFF;
	    return new int[] { r, g, b };
	}

	public static int integrar(int[] rgb) {
	    int r = (rgb[0] & 0xFF) << 16;
	    int g = (rgb[1] & 0xFF) <<  8;
	    int b = (rgb[2] & 0xFF) <<  0;
	    return r | g | b;
	}
	
}


