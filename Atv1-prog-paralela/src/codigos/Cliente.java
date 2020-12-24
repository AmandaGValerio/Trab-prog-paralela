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
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;

public class Cliente {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//conectando no servidor
			Socket cliente = new Socket("127.0.0.1", 8090);
			//se conectado, exibe uma mensagem
			System.out.println("Cliente conectado!");
			
			//enviar a imagem aqui	        
			FileInputStream fis = null;
		    BufferedInputStream bis = null;
		    OutputStream os = null;
			File myFile = new File ("imagem.jpg");
			byte [] mybytearray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			os = cliente.getOutputStream();
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			System.out.println("Done.");
			
			
			//recebendo a imagem
			int bytesRead;
			int FILE_SIZE = 72000; //o tamanho deve ser suficiente para comportar o arquivo, já que o DO não funciona
		    int current;
		    FileOutputStream fos = null;
		    BufferedOutputStream bos = null;
		    System.out.println("entrei no try");
			byte [] mybytearray2  = new byte [FILE_SIZE];
			InputStream is = cliente.getInputStream();
			fos = new FileOutputStream("imagensResultantes/recebida.jpg");
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray2, 0, mybytearray2.length);
			current = bytesRead;
			
			//por algum motivo que eu não consegui descobrir, o DO começou a dar um loop infinito 
			//e não está atualizando as variaveis internas 
//			do {
//				bytesRead = is.read(mybytearray2, current, (mybytearray2.length - current));
//				if(bytesRead >= 0) { 
//					current += bytesRead;
//				}
//			} while(bytesRead > -1);
//			bos.write(mybytearray2, 0 , current);			
			bos.write(mybytearray2); 
			bos.flush();
			System.out.println("recebi");
			
			
			//fechando as conexoes
			if (bis != null) bis.close();
			if (os != null) os.close();
			bos.close();
			cliente.close();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
