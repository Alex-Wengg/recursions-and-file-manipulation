import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class PartImage {
    private boolean[][]	pixels;
    private boolean[][]	visited;
    private int	rows;
    private int	cols;

    //Creates a new, blank PartImage with the given rows (r) and columns (c)
    public PartImage(int r, int c) {
        rows = r;
        cols = c;
        visited = new boolean[r][c];
        pixels = new boolean[r][c];
    }

    //Creates a new PartImage containing rw rows and cl columns
    //Initializes the 2D boolean pixel array based on the provided byte data
    //A 0 in the byte data is treated as false, a 1 is treated as true
    public PartImage(int rw, int cl, byte[][] data) {
        this(rw,cl);



        for (int r=0; r<10; r++) {
            for (int c=0; c<10; c++) {
                if (data[r][c] == 1)
                    pixels[r][c] = true;
                else
                    pixels[r][c]= false;
            }
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean getPixel(int r, int c) { return pixels[r][c]; }



    public Point2D findStart() {
       // System.out.println(pixels);
        for (int r=0; r<pixels.length; r++) {
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                     return new Point2D(r,c);
                }
            }
        }
        return null;
    }

    public int partSize() {
        return 0;
    }

    private void expandFrom(int r, int c) {

    }

    private int perimeterOf(int r, int c) {
        return 0;
    }

    public boolean isBroken(){
        Point2D p = findStart();
        expandFrom((int)p.getX(), (int)p.getY());
        return (partSize() != 0);
    }

    public int perimeter() {
        Point2D p = findStart();
        return perimeterOf((int)p.getX(), (int)p.getY());
    }

    public int countPieces(){
        return -1;
    }

    public void print() {
        System.out.println("pixels");
    }

    public static PartImage readFromFile(String fileName) throws InvalidPartImageException{
         try {
             File dir = new File( System.getProperty("user.dir"));
              String printFile = new String(Files.readAllBytes(Paths.get(fileName)));
             String filepath = dir+ "/"+fileName;

             File file = new File(filepath);

             Scanner checkLength = new Scanner(file);
             int length =  checkLength.nextLine().length();

             while (checkLength.hasNextLine()) {
                 if (checkLength.nextLine().length()!=length){
                     throw new InvalidPartImageException(fileName);
                 }
             }

             Scanner checkNumbers = new Scanner(file);
             ArrayList<String> list=new ArrayList<>();

             while (checkNumbers.hasNextLine()) {
                 list.add(String.valueOf(checkNumbers.nextLine()));
             }
             for (int i=0; i<list.size(); i++) {

                 //we loop through the numbers ignoring the commas to see if there are non 1 and 0s
                 for (int j=0;j < list.get(i).length();j+=2){
                     if(!(list.get(i)).substring(j,j+1).contains("1")) {
                         if(!(list.get(i)).substring(j,j+1).contains("0")) {
                             throw new InvalidPartImageException(fileName);
                         }
                     }
                 }
             }

             Byte[][] data = new Byte[list.size()][list.get(0).length()];
             for (int i=0; i<list.size(); i++) {
                 for (int j=0;j < list.get(i).length();j+=2) {
                     data[i][j] = Byte.valueOf((list.get(i)).substring(j,j+1));
             }}
           //
             //
             //  System.out.println(data);

             return new PartImage(list.size(), list.get(0).length());

         } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for reading");
        } catch (IOException e) {
            System.out.println("Error: Cannot read from file");
        }
        return null;    }
}
