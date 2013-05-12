/**
 * 
 */
package com.eventpool.common.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImageProcessor {
	static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);
	
	//@Value("${image-read-timeout}")
	private int readTimeout=2*60*1000;
	
	//@Value("${image-connection-timeout}")
	private int connectionTimeout=2*60*1000;
	
	public ImageProcessor() {
		// TODO Auto-generated constructor stub
	}
	protected BufferedImage getSourceImage(String resource) throws Exception{
		URL url = new URL(resource);		
		return getSourceImage(url);
	}
	protected BufferedImage getSourceImage(URL url) throws IOException{
		URLConnection conn = null;
		InputStream inStream = null;
		BufferedImage srcImage = null;
		try{
			conn = url.openConnection();
			conn.setConnectTimeout(connectionTimeout);
			conn.setReadTimeout(readTimeout);
			inStream = conn.getInputStream();
			srcImage = ImageIO.read(inStream);
			if(srcImage == null){
				throw new IOException("Error in getting imageSource for url = " + url.toString());
			}
		}finally{
			if(inStream != null){
				inStream.close();
			}
		}
		return srcImage;
	}
	
	public BufferedImage getSourceImage(File file) throws IOException {
		BufferedImage srcImage = ImageIO.read(file);
		return srcImage;
	}
	public BufferedImage trim ( BufferedImage image ){
		int j;
		int trimFromTopUnits=0,trimFromBottomUnits=0,trimFromRightUnits=0,trimFromLeftUnits=0;
		if ( image.getHeight() > 0 ){
			// trim from top
		    for ( int i=0; i<image.getHeight() ; i++ ){
		    	for ( j=0 ; j<image.getWidth() ; j++ ){
		    		if ( image.getRGB(j,i) != Color.WHITE.getRGB() ){
		    			break;
		    		}
		    	}
		    	if ( j==image.getWidth()){
		    		trimFromTopUnits++;
		    	}else{
		    		break;
		    	}
		    }
		    // trim from bottom
		    for ( int i=image.getHeight()-1 ; i>=0 ; i--){
		    	for ( j=0 ; j<image.getWidth() ; j++ ){
		    		if ( image.getRGB(j,i) != Color.WHITE.getRGB() ){
		    			break;
		    		}
		    	}
		    	if ( j==image.getWidth()){
		    		trimFromBottomUnits++;
		    	}else {
		    		break;
		    	}
		    }
		    // trim from left
		    for ( int i=0; i<image.getWidth() ; i++ ){
		    	for ( j=0 ; j<image.getHeight() ; j++ ){
		    		if ( image.getRGB(i,j) != Color.WHITE.getRGB() ){
		    			break;
		    		}
		    	}
		    	if ( j==image.getHeight()){
		    		trimFromLeftUnits++;
		    	}else{
		    		break;
		    	}
		    }
		    // trim from right
		    for ( int i=image.getWidth()-1 ; i>=0 ; i--){
		    	for ( j=0 ; j<image.getHeight() ; j++ ){
		    		if ( image.getRGB(i,j) != Color.WHITE.getRGB() ){
		    			break;
		    		}
		    	}
		    	if ( j==image.getHeight()){
		    		trimFromRightUnits++;
		    	}else {
		    		break;
		    	}
		    }
		}
		if ( trimFromTopUnits + trimFromBottomUnits < image.getHeight() && trimFromLeftUnits + trimFromRightUnits < image.getWidth()){
			int newWidth = image.getWidth()-trimFromLeftUnits-trimFromRightUnits;
			int newHeight = image.getHeight()-trimFromTopUnits-trimFromBottomUnits;
		    return image.getSubimage(trimFromLeftUnits, trimFromTopUnits,newWidth,newHeight);
		}
		return null;
	}
	
	public boolean trimImage (BufferedImage srcImage,String destFile) throws Exception {
		BufferedImage trimmedImage = trim(srcImage);
		writeImageToDisk(trimmedImage,destFile,0.9f);
		//ImageIO.write(trimmedImage,"jpg",new File(destFile));
		return true;
	}
	
	/**
	 * resize this.srcImage to a square of side 'sideLength' and the write to a file on disk
	 * The image is resized first maintaining its proportion, then some padding is used.
	 * @param sideLength - measure of side of the square 
	 * @param destFile - location of the file to write to
	 * @return - true if successful otherwise false
	 * @throws IOException
	 */
	public boolean reSizeImage (BufferedImage srcImage, int sideLength ,String destFile, float imageQuality) throws IOException{
		return reSizeImage ( srcImage,sideLength,sideLength,destFile, imageQuality);
	}
	
	
	/**
	 * Given a BufferedImage, write it to disk
	 * The default image format is jpeg
	 * @param image - The BufferedImage to save 
	 * @param destFile - location at which the image is to be saved
	 * @param imageQuality - QFactor, compression ratio. Range - 0-1
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeImageToDisk(BufferedImage image , String destFile , float imageQuality) throws FileNotFoundException, IOException{
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter)iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		iwp.setCompressionQuality(imageQuality);
		File file = new File(destFile);
		FileImageOutputStream output = new FileImageOutputStream(file);
		writer.setOutput(output);
		IIOImage img = new IIOImage(image, null, null);
		writer.write(null, img, iwp);
		writer.dispose();
	}
	
	/**
	 * Resize this.srcImage to the given dimensions and write to a file on disk.
	 * The image is resized maintaining its proportion, then padding is used to get the required dimensions.
	 * @param width - expected width of resized file
	 * @param height - expected height of resized file
	 * @param destFile - location of the file to write to
	 * @return - true if successful, false otherwise
	 * @throws IOException
	 */
	public boolean reSizeImage( BufferedImage srcImage,int width, int height ,String destFile, float imageQuality) throws IOException{
		BufferedImage scaledImage = scaleTo(srcImage,width,height);
		writeImageToDisk(scaledImage, destFile, imageQuality);
		//ImageIO.write(scaledImage,"jpg",new File(destFile));
		return true;
	}
	
	/**
	 * Given a buffered image, resize it by padding and return a new BufferedImage. This method does nothing to the original image.
	 * It just adds extra padding if the required dimensions are more than the actual image dimensions.
	 * @param image - source Image
	 * @param newWidth - new Width
	 * @param newHeight - new Height
	 * @return - a new BufferedImage object formed by padding the original image
	 */
	public BufferedImage getPaddedImage ( BufferedImage image , int newWidth , int newHeight , Color color){
		
		int x = (newWidth - image.getWidth())/2;
		int y = (newHeight - image.getHeight())/2;
		
		if(x == 0 && y == 0){
			return image;//No padding needed
		}
		boolean colorHasAlpha = (color.getAlpha() != 255);
		boolean imageHasAlpha = (image.getTransparency() != BufferedImage.OPAQUE);
		
		BufferedImage paddedImage;
		if(colorHasAlpha || imageHasAlpha){
			paddedImage = new BufferedImage(newWidth,newHeight, BufferedImage.TYPE_INT_ARGB);
		}
		else{
			paddedImage = new BufferedImage(newWidth,newHeight, image.getType());//Rather then using TYPE_INT_RGB using getType()
		}
		Graphics g = paddedImage.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, newWidth, newHeight);

		g.drawImage(image, x, y, null);
		g.dispose();
		return paddedImage;
	}
	
	public BufferedImage scaleTo(BufferedImage srcImage, int width , int height){
		return getPaddedImage(Scalr.resize(srcImage, Scalr.Method.QUALITY, width, height), width, height, Color.WHITE);
	}
	
}
