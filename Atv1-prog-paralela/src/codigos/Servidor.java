//autores: Amanda, Lucas Alves

package codigos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
			
			//dividir em 4 threads
			
			//executar o algoritmo nas 4 threads ao mesmo tempo
			
			//retornar a imagem em preto e branco
			
			//fechando as conexoes
			servidor.close();
			cliente.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

}
