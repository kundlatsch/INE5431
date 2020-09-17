
import java.awt.image.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageApp   {
	
	// Leitura da imagem
	public static BufferedImage loadImage(String surl) {  
		BufferedImage bimg = null;  
		try {  
			URL url = new URL(surl);
			bimg = ImageIO.read(url);  
			//bimg = ImageIO.read(new File("D:/Temp/mundo.jpg"));
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return bimg;  
	}  
	
	public void apresentaImagem(JFrame frame, BufferedImage img) {
		frame.setBounds(0, 0, img.getWidth(), img.getHeight());
		JImagePanel panel = new JImagePanel(img, 0, 0);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public static BufferedImage criaImagemRGB() {
		BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = img.getRaster();
		
		for(int h=0;h<img.getHeight();h++) //Percorre a horizontal
			for(int w=0;w<img.getWidth();w++) {//Percorre a vertical
				raster.setSample(w,h,0,220); // Componente Vermelho
				raster.setSample(w,h,1,219); // Componente Verde
				raster.setSample(w,h,2,97);  // Componente Azul
			} 
		return img;
	}
	
	public static BufferedImage criaImagemCinza(BufferedImage bufferedImage) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = img.getRaster();

		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int rgb = bufferedImage.getRGB(w, h);
				int r = (int)((rgb&0x00FF0000)>>>16);
				int g = (int)((rgb&0x0000FF00)>>>8);
				int b = (int)(rgb&0x000000FF);
				double y = (0.3*r) + (0.59*g) + (0.11*b); 
				raster.setSample(w, h, 0, y);
			}
		}
		return img;
	}
	
	public static BufferedImage criaImagemBinaria(BufferedImage bufferedImage) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster raster = img.getRaster();
		WritableRaster rasterPB = bufferedImage.getRaster();

		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				int[] p = new int[1];
				rasterPB.getPixel(w, h, p);
				if(p[0] > 127) {
					raster.setSample(w, h, 0, 1);
				} else {
					raster.setSample(w, h, 0, 0);
				}
			}
		}
		return img;
	}
	
	// Imprime valores dos pixeis de imagem RGB
	public static void  imprimePixeis(BufferedImage bufferedImage) {
		for(int h=0;h<bufferedImage.getHeight();h++) //Percorre a horizontal
			for(int w=0;w<bufferedImage.getWidth();w++) {//Percorre a vertical
				int rgb = bufferedImage.getRGB(w,h);
				int r = (int)((rgb&0x00FF0000)>>>16); // componente vermelho
				int g = (int)((rgb&0x0000FF00)>>>8); // componente verde
				int b = (int)(rgb&0x000000FF); //componente azul
				System.out.print("at ("+w+","+h+"): ");
				System.out.println(r+","+g+","+b);
			}
	}

	public static BufferedImage reduzirResolucao(BufferedImage img, int proporcao) {
		int newWidth = img.getWidth() / proporcao;
		int newHeight = img.getHeight() / proporcao;
		return null;
	}

	public static void main(String[] args) {
		ImageApp ia = new ImageApp();
		// Required image: https://www.inf.ufsc.br/~roberto.willrich/INE5431/circle.png
		// Alternative image: http://www.inf.ufsc.br/~roberto.willrich/INE5431/peixe.png
		BufferedImage imgJPEG = loadImage("https://www.inf.ufsc.br/~roberto.willrich/INE5431/circle.png");

		// Questão 1
		// TODO: concluir o método utilizado abaixo
		// BufferedImage imgReduzida = reduzirResolucao(imgJPEG, 4);

		// Questão 2
		BufferedImage imgCinza = criaImagemCinza(imgJPEG);
		
		// Questão 3
		BufferedImage imgBinaria = criaImagemBinaria(imgCinza);

		// Questão 4
		// TODO: split RGB

		ia.apresentaImagem(new JFrame("imgJPEG"), imgJPEG);
		ia.apresentaImagem(new JFrame("imgCinza"), imgCinza);
		ia.apresentaImagem(new JFrame("imgBinaria"), imgBinaria);

		imprimePixeis(imgJPEG);
	}
}