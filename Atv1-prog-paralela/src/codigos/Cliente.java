//autores: Amanda, Lucas Alves

package codigos;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//conectando no servidor
			Socket cliente = new Socket("127.0.0.1", 8090);
			//se conectado, exibe uma mensagem
			System.out.println("Cliente conectado!");
			
			//enviar a imagem aqui
			
			//fechando as conexoes
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
