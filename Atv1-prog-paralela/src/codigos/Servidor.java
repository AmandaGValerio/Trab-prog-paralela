//autores: Amanda, Lucas Alves

package codigos;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
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
			byte[] objectAsByte = new byte[cliente.getReceiveBufferSize()];
	        BufferedInputStream bf = new BufferedInputStream(cliente.getInputStream());
	        bf.read(objectAsByte);
			ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
	        
//			esta linha serve apenas para testar o algoritmo		
//			BufferedImage imagem = ImageIO.read(new File("imagem.jpg"));
			
			Object obj = null;
		     ByteArrayInputStream bis = null;
		     ObjectInputStream ois = null;
		     bis = new ByteArrayInputStream(objectAsByte);
		     ois = new ObjectInputStream(bis);
		     try {
				obj = ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		        bis.close();
		        ois.close();
		        BufferedImage imagem = (BufferedImage) obj;
			
			
			//dividindo a imagem
			int largura = imagem.getWidth();
	        int altura = imagem.getHeight();
	        BufferedImage processada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
	        
	        
	        //dividir em 4 threads
	        ConverterImg thread1(); //passar os parâmetros
	        ConverterImg thread2();
	        ConverterImg thread3();
	        ConverterImg thread4();
	        
			//executar o algoritmo nas 4 threads ao mesmo tempo
	        thread1.run();
	        thread2.run();
	        thread3.run();
	        thread4.run();

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

	
}


