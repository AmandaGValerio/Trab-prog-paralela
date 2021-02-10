//autores: Amanda, Lucas Alves

package codigos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	public static void main(String[] args) {
		try {
			//conectando no servidor
			Socket cliente = new Socket("127.0.0.1", 8090);
			//se conectado, exibe uma mensagem
			System.out.println("Cliente conectado!");
			
			//enviar a imagem aqui	        
			FileInputStream fentradaC = null;
		    BufferedInputStream buffentradaC = null;
		    OutputStream saidaC = null;
			File arqEnviar = new File ("imagem.jpg");
			byte [] mybytearray  = new byte [(int)arqEnviar.length()];
			fentradaC = new FileInputStream(arqEnviar);
			buffentradaC = new BufferedInputStream(fentradaC);
			buffentradaC.read(mybytearray,0,mybytearray.length);
			saidaC = cliente.getOutputStream();
			saidaC.write(mybytearray,0,mybytearray.length);
			saidaC.flush();		
			
			//recebendo a imagem
			int lidoC, posAtualC;
		    FileOutputStream fsaida = null;
		    BufferedOutputStream buffsaida = null;
			byte [] imgBytesC  = new byte [65536]; //tamanho máximo permitido
			InputStream entradaC = cliente.getInputStream();
			fsaida = new FileOutputStream("imagensResultantes/recebida.jpg");
			buffsaida = new BufferedOutputStream(fsaida);
			lidoC = entradaC.read(imgBytesC, 0, imgBytesC.length);
			posAtualC = lidoC;			
			do {
				lidoC = entradaC.read(imgBytesC, posAtualC, (imgBytesC.length - posAtualC));
				if(lidoC >= 0) { 
					posAtualC = posAtualC + lidoC;
				}
			} while(lidoC > -1);
			buffsaida.write(imgBytesC, 0 , posAtualC);			
			buffsaida.flush();		
			
			//fechando as conexoes
			buffentradaC.close();
			saidaC.close();
			buffsaida.close();
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
