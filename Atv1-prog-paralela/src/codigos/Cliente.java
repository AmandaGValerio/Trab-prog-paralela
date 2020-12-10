//autores: Amanda, Lucas Alves

package codigos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class Cliente {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//conectando no servidor
			Socket cliente = new Socket("127.0.0.1", 8090);
			//se conectado, exibe uma mensagem
			System.out.println("Cliente conectado!");
			
			//enviar a imagem aqui
//			JFileChooser arq = new JFileChooser();
//			File f;
//			ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
//			arq.showOpenDialog(null);
//	        f = arq.getSelectedFile();
//	        String foto = f.getAbsolutePath();
	        
	        BufferedImage imagem = ImageIO.read(new File("imagem.jpg"));
	        
			
			
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
