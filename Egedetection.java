package com.company;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Main {
    static BufferedImage inputImg,outputImg;
    static int[][] pixelMatrix=new int[3][3];

    public static void main(String[] args) throws IOException {
        try {

            inputImg=ImageIO.read(new File("D:/visual_work/lg_ver.jpg"));
            outputImg=new BufferedImage(inputImg.getWidth(),inputImg.getHeight(),TYPE_INT_RGB);

            for(int i=1;i<inputImg.getWidth()-1;i++){
                for(int j=1;j<inputImg.getHeight()-1;j++){
                    pixelMatrix[0][0]=new Color(inputImg.getRGB(i-1,j-1)).getRed();
                    pixelMatrix[0][1]=new Color(inputImg.getRGB(i-1,j)).getRed();
                    pixelMatrix[0][2]=new Color(inputImg.getRGB(i-1,j+1)).getRed();
                    pixelMatrix[1][0]=new Color(inputImg.getRGB(i,j-1)).getRed();
                    pixelMatrix[1][2]=new Color(inputImg.getRGB(i,j+1)).getRed();
                    pixelMatrix[2][0]=new Color(inputImg.getRGB(i+1,j-1)).getRed();
                    pixelMatrix[2][1]=new Color(inputImg.getRGB(i+1,j)).getRed();
                    pixelMatrix[2][2]=new Color(inputImg.getRGB(i+1,j+1)).getRed();

                    int edge=(int) convolution(pixelMatrix);
                    outputImg.setRGB(i,j,(edge<<16 | edge<<8 | edge));
                }
            }

            File outputfile = new File("D:/visual_work/edge.jpg");
            ImageIO.write(outputImg,"jpg", outputfile);


        } catch (IOException ex) {System.err.println("Image width:height="+inputImg.getWidth()+":"+inputImg.getHeight());}
    }
    public static double convolution(int[][] pixelMatrix){

        int gy=(pixelMatrix[0][0]*-1)+(pixelMatrix[0][1]*-2)+(pixelMatrix[0][2]*-1)+(pixelMatrix[2][0])+(pixelMatrix[2][1]*2)+(pixelMatrix[2][2]*1);
        int gx=(pixelMatrix[0][0])+(pixelMatrix[0][2]*-1)+(pixelMatrix[1][0]*2)+(pixelMatrix[1][2]*-2)+(pixelMatrix[2][0])+(pixelMatrix[2][2]*-1);
        return Math.sqrt(Math.pow(gy,2)+Math.pow(gx,2));
    }
}
