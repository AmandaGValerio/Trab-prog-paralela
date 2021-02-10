//autores: Amanda, Lucas Alves

package codigos;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Servidor{

	public static void main(String[] args) {
		try {
			//iniciando o servidor
			ServerSocket servidor = new ServerSocket(8090);
			System.out.println("Servidor na porta 8090 aguardando...");
			
			//esperando por uma conexao
			Socket cliente = servidor.accept();
			
			//se uma conexão for estabelecida
			System.out.println("Nova conexão " + cliente.getInetAddress().getHostAddress());
			
			// ler a imagem 			
			int lido, posAtual;
		    FileOutputStream fsaida = null;
		    BufferedOutputStream buffsaida = null;
			byte [] imgBytes  = new byte [65536]; //tamanho máximo permitido
			InputStream entrada = cliente.getInputStream();
			fsaida = new FileOutputStream("ima.jpg");
			buffsaida = new BufferedOutputStream(fsaida);
			lido = entrada.read(imgBytes, 0, imgBytes.length);
			posAtual = lido;
			
			//por algum motivo que eu não consegui descobrir, o DO começou a dar um loop infinito 
			//e não está atualizando as variaveis internas 
			
//			do {
//				System.out.println("input " + lido + " atual " + posAtual);
//				lido = entrada.read(mybytearray, posAtual, (mybytearray.length - posAtual));
//				if(lido >= 0) { 
//					posAtual = posAtual + lido;
//				}
//			} while(lido > -1);
//			bos.write(mybytearray, 0 , posAtual);	
			
			buffsaida.write(imgBytes); 
			buffsaida.flush();	
			
//			esta linha serve apenas para testar o algoritmo		
			BufferedImage imagem = ImageIO.read(new File("ima.jpg"));
		
			//dividindo a imagem
			int largura = imagem.getWidth();
	        int altura = imagem.getHeight();
	        int larMeio, altMeio;
	        larMeio = largura/2;
	        altMeio = altura/2;
	        
	        //definindo o tamanho da imagem de saída
	        BufferedImage processada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);	                
	        
	        //dividir em 4 threads
	        ConverterImg thread1 = new ConverterImg(0, altMeio, 0, larMeio, imagem, processada); 
	        ConverterImg thread2 = new ConverterImg(0, altMeio, larMeio, largura, imagem, processada); 
	        ConverterImg thread3 = new ConverterImg(altMeio, altura, 0, larMeio, imagem, processada); 
	        ConverterImg thread4 = new ConverterImg(altMeio, altura, larMeio, largura, imagem, processada);  
	        
			//executar o algoritmo nas 4 threads ao mesmo tempo
	        thread1.run();
	        thread2.run();
	        thread3.run();
	        thread4.run();

			//salvar a imagem em preto e branco
	        ImageIO.write(processada, "jpg", new File("pretobranco.jpg"));
	        
	        //retornar a imagem
	        FileInputStream fentrada = null;
		    BufferedInputStream buffentrada = null;
		    OutputStream saida = null;
			File arqDevolver = new File ("pretobranco.jpg");
			byte [] imgBytesDev  = new byte [(int)arqDevolver.length()];
			fentrada = new FileInputStream(arqDevolver);
			buffentrada = new BufferedInputStream(fentrada);
			buffentrada.read(imgBytesDev, 0, imgBytesDev.length);
			saida = cliente.getOutputStream();
			saida.write(imgBytesDev, 0, imgBytesDev.length);
			
			saida.flush();
			
			//fechando as conexoes
			fsaida.close();
			buffsaida.close();
			buffentrada.close();
			saida.close();        
			servidor.close();
			cliente.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	
}


