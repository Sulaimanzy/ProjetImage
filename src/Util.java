import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

public class Util {


    public static void toBinaire(BufferedImage img,int seuil) {
        Color black = new Color(0,0,0);
        Color white = new Color(255,255,255);
        for(int i = 0;i<img.getWidth();i++) {
            for(int j=0;j<img.getHeight();j++) {
                Color tab = new Color(img.getRGB(i, j));
                if(tab.getBlue()<seuil||tab.getRed()<seuil||tab.getGreen()<seuil)img.setRGB(i, j, black.getRGB());
                else img.setRGB(i, j, white.getRGB());
            }
            //if((tab.getBlue()==tab.getRed())&&(tab.getRed()==tab.getGreen()))img.setRGB(i, j, white.getRGB());
            //if(!(tab.getBlue()==tab.getRed())&&!(tab.getRed()==tab.getGreen()))img.setRGB(i, j, black.getRGB());
        }

    }
    public static BufferedImage chargerImage(String nom) {
        File chemin = new File(nom);
        BufferedImage image = null;
        try {
            image = ImageIO.read(chemin);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //Applique un produit de convolution sur une image

    public static BufferedImage convolutionFilter(BufferedImage image, float[][] filtres) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int colonne = filtres.length;
        int  ligne = filtres[0].length;

        float[] filtre = new float[colonne*ligne];
        for (int j = 0; j<ligne; j++) {
            for (int i= 0; i< colonne; i++ ) {
                filtre[j*colonne + i] = filtres[j][i];
            }
        }

        Kernel kern = new Kernel(ligne, colonne, filtre);
        ConvolveOp op = new ConvolveOp(kern);

        newImage = op.filter(image,null);
        return newImage;
    }

    public static BufferedImage centre(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        BufferedImage newImage = image.getSubimage(width/2, 0, 1, height);
        return newImage;
    }
    public static int nombreMarche(BufferedImage image) {
        int marche = 0;
        Color blanc = new Color(255,255,255);
        int derniereDistance = 0;
        int actuelDistance = 0;
        int actuel=0;
        int precedent=0;
        int precedenteCouleur = blanc.getRGB();

        for (int x = image.getHeight()-1; x > 0; x--) {
            if(precedenteCouleur!=image.getRGB(0, x)){
                    precedenteCouleur = image.getRGB(0,x);
                    derniereDistance = actuelDistance;
                    precedent = actuel;
                    actuel = x;
                    actuelDistance = precedent-actuel;
/*
                if(derniereDistance>actuelDistance){
                    marche++;
                    marche++;
                }
                else marche++;
                */
                marche++;
            }


        }

        return marche/2;
        }

    public static void imshow(BufferedImage image) throws IOException {
        //Encoding the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "jpg", baos );

        //Storing the encoded Mat in a byte array
        byte[] byteArray = baos.toByteArray();

        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);

        //Instantiate JFrame
        JFrame frame = new JFrame();

        //Set Content to the JFrame
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);
    }
    public static void toGray(BufferedImage img) {
        for(int i = 0;i<img.getWidth();i++) {
            for(int j=0;j<img.getHeight();j++) {
                Color tab = new Color(img.getRGB(i, j));
                int color = (tab.getRed()+tab.getBlue()+tab.getGreen())/3;
                Color coloor = new Color(color,color,color);
                img.setRGB(i, j, coloor.getRGB());
            }
        }
    }
    public static String menu(){
        System.out.println(" Bienvenue sur l'application permettant de compter le nombre de marche sur un escalier ");
        System.out.println(" On rappelle les contraintes suivante dans le choix de l'escalier : ");
        //Histogramme médiane ?
        System.out.println(" - L'escalier doit être centré ");
        //Otsu ?
        System.out.println(" - Si l'escalier est unicolore il ne doit pas être trop claire ");
        //Couper l'image apres detection d'une forme etrange ?
        System.out.println(" - L'image ne doit pas avoir trop d'élement exterieur  ");
        System.out.println(" Veuillez choisir une image :");
        Scanner sc = new Scanner(System.in);
        String s = "";
        do{
            System.out.println(s);
            s = sc.nextLine();
        }while(s.equals(""));
        return s;
    }
    public static int estBicolore(){
        System.out.println(" Votre escalier est il bicolore ? ");
        System.out.println(" 0 - Oui ");
        System.out.println(" 1 - Non ");
        Scanner sc = new Scanner(System.in);
        int x=-1;
        do{
            x = sc.nextInt();
        }while((x!=0)&&(x!=1));

        return x;
    }

}
