import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        //BufferedImage image = Util.chargerImage("C:\\Users\\MZ\\Pictures\\projetImage"+File.separator+"escalier5.jpg");
        //int isBic = 1;
        BufferedImage image = Util.chargerImage(System.getProperty("user.dir")+File.separator+Util.menu()+".jpg");
        int isBic = Util.estBicolore();
        //L'image passe en niveau de gris
        Util.toGray(image);

        //On lui applique un filtre horizontal
        float[][] filtreHorizontal = { { -1, -2, -1},
                                       {  0,  0,  0},
                                       {  1,  2,  1} };

        BufferedImage image1 = Util.convolutionFilter(image,filtreHorizontal);
        //Util.imshow(image1);

       /*
        //On lui applique un filtre laplacien
        float[][] filtreLaplacien = { {  0, -1,  0},
                                      { -1,  4, -1},
                                      {  0, -1,  0} };

        BufferedImage image2 = Util.convolutionFilter(image1,filtreLaplacien);
        Util.imshow(image2);


        */
        //On fait passer l'image en binaire
        //otsu ?
        Util.toBinaire(image1,60);
        Util.imshow(image1);

        BufferedImage image3 ;
        BufferedImage image4 ;
        //On aplique une ouverture à l'image l'ordre se fait en fonction de la bicolorité

        if(isBic==0){
            image3 = MorphMath.close(image1, 3);
            image4 = MorphMath.open(image3, 2);
        }
        else {
            image3 = MorphMath.open(image1, 2);
            image4 = MorphMath.close(image3, 2);
        }

        Util.imshow(image3);
        Util.imshow(image4);

        //On aplique une ouverture à l'image
        //Util.imshow(image4);


        //Util.toBinaire(image4,60);
        //Util.imshow(image4);


        //Compte le nombre de marches en prennant seulement la partie centrale de l'image
        BufferedImage imageCentral = Util.centre(image4);
        int nb_marche = Util.nombreMarche(imageCentral);
        if(nb_marche==0){
            imageCentral = Util.centre(image1);
            nb_marche = Util.nombreMarche(imageCentral);
        }
        System.out.println("Votre escalier à "+nb_marche+" marches");
    }

}
