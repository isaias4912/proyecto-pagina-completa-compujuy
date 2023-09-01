/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.api.utils;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;

/**
 *
 * @author rafael
 */
public class ImageUtils {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ImageUtils.class);

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int type = (inputImage.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, type);

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    public static void resize(BufferedImage inputImage,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        int type = (inputImage.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, type);

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    /**
     * Resize de imagenes a la altura de altura
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param altura que tendra la imagen
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, int altura) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        double percent = (altura * 100) / inputImage.getHeight();
        percent = percent / 100;
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    /**
     * Resize de imagenes a la altura de altura
     *
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param altura que tendra la imagen
     * @throws IOException
     */
    public static void resize(BufferedImage inputImage,
            String outputImagePath, int altura) throws IOException {
        double percent = (altura * 100) / inputImage.getHeight();
        percent = percent / 100;
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImage, outputImagePath, scaledWidth, scaledHeight);
    }

    public static String getBase64(String inputImagePath) {
        Path path = Paths.get(inputImagePath);
        String encodedfile = null;
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException ex) {
            logger.error("Error en la lectura del archivo para pasar a base64 una imagen", ex);
        }
        try {
            encodedfile = new String(Base64.encodeBase64(data), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.error("Error  para pasar a base64 una imagen", ex);
        }
        return encodedfile;
    }
}
