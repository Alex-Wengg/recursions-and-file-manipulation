import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

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

    public void print() {

    }

    public Point2D findStart() {
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

    public static PartImage readFromFile(String fileName) throws InvalidPartImageException{
         try {
             File dir = new File( System.getProperty("user.dir"));
            // System.out.println(dir+"/"+fileName);

              System.out.println(" \n part something");

             BufferedReader br = new BufferedReader(new FileReader((dir+"/"+fileName)));
             FileInputStream printLine = new FileInputStream(((dir+"/"+fileName)));
             FileInputStream checkLine = new FileInputStream(((dir+"/"+fileName)));

             int fileLength=(br.readLine().length());
             ArrayList<Character> tmp = new ArrayList<Character>();

            String st;
             while(( st= br.readLine()) != null) {
                 System.out.println(st);
             }

             while(printLine.available() > 0){
                 System.out.print((char)printLine.read() );
             }



             printLine.close();
             checkLine.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for reading");
        } catch (IOException e) {
            System.out.println("Error: Cannot read from file");
        }
        return null;
    }
}
