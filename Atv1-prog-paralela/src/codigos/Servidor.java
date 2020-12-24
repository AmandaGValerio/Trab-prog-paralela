//autores: Amanda, Lucas Alves

package codigos;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class Servidor{

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
			int bytesRead;
			int FILE_SIZE = 65536; //o tamanho deve ser suficiente para comportar o arquivo, já que o DO não funciona
		    int current;
		    FileOutputStream fos = null;
		    BufferedOutputStream bos = null;
		    System.out.println("entrei no try");
			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = cliente.getInputStream();
			fos = new FileOutputStream("ima.jpg");
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;
			System.out.println("input " + bytesRead + " atual " + current);
			//por algum motivo que eu não consegui descobrir, o DO começou a dar um loop infinito 
			//e não está atualizando as variaveis internas 
//			do {
//				System.out.println("input " + bytesRead + " atual " + current);
//				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
//				if(bytesRead >= 0) { 
//					current += bytesRead;
//				}
//			} while(bytesRead > -1);
//			System.out.println("sai do try");
//			bos.write(mybytearray, 0 , current);			
			bos.write(mybytearray); 
			bos.flush();
			System.out.println("recebi");
			
			
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
	        FileInputStream fis = null;
		    BufferedInputStream bis = null;
		    OutputStream os = null;
			File myFile = new File ("pretobranco.jpg");
			byte [] mybytearray1  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray1,0,mybytearray1.length);
			os = cliente.getOutputStream();
			os.write(mybytearray1,0,mybytearray1.length);
			os.flush();
			System.out.println("Done2.");
			
			
			
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (bis != null) bis.close();
			if (os != null) os.close();
	        
			
			//fechando as conexoes
			servidor.close();
			cliente.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	
}


