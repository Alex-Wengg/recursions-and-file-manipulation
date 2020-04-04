import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files
import java.nio.file.*;

public class PartImage {
    private boolean[][]	pixels;
    private boolean[][]	visited;
    private int	rows;
    private int	cols;
    int count=0;

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
    public PartImage(int rw, int cl, Byte[][] data) {
        this(rw, cl);


        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                if (data[r][c] !=null){
                    if (data[r][c] == 1) {
                         pixels[r][c] = true;
                    } else {
                         pixels[r][c] = false;
                    }
                }
            }
        }


    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean getPixel(int r, int c) { return pixels[r][c]; }



    public Point2D findStart() {
        for (int r=0; r<pixels.length; r++) {
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                   // System.out.println(pixels[r][c]);

                    return new Point2D(r,c);
                }
            }
        }
        return null;
    }

    public int partSize() {
        int count = 0;
        for (int r=0; r<pixels.length; r++) {
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                    count++;

                }
            }
        }

        return count;
    }

    private void expandFrom(int r, int c) {

        if (pixels[r][c] == true) {
            pixels[r][c]=false;
        }
        if (r+1 < pixels.length){
            if (pixels[r+1][c] == true) {
                 expandFrom(r + 1, c);
            }
        }
        if (r-1 >=0){
            if (pixels[r-1][c] == true) {
                  expandFrom(r - 1, c);
            }
        }
        if (c-1 >=0){
            if (pixels[r][c-1] == true) {
                  expandFrom(r , c-1);
            }
        }
        if (c+1 < pixels[r].length){
            if (pixels[r][c+1] == true) {
                  expandFrom(r, c+1);
            }
        }
    }

    private int perimeterOf(int r, int c) {
        boolean occupied = true;
        if (pixels[r][c] == true) {
            visited[r][c]=occupied;
        }
        if ((r+1 < pixels.length)||(r-1 >=0)) {
            if ((pixels[r + 1][c] == false) || (pixels[r - 1][c] == false)) {
                if ((visited[r][c - 1] != occupied) && (pixels[r][c - 1] == true)) {
                    visited[r][c - 1] = occupied;
                    count++;

                    perimeterOf(r, c - 1);
                }
                if ((visited[r][c + 1] != occupied) && (pixels[r][c + 1] == true)) {
                    visited[r][c + 1] = occupied;
                    count++;

                    perimeterOf(r, c + 1);
                }
            }
        }
        private int perimeterOf(int r, int c) {
            boolean occupied = true;
            visited[r][c] = occupied;

            return (

                    (r < cols-1 && !visited[r+1][c] &&
                            pixels[r+1][c]) ? perimeterOf(r+1,c) : 0) + ((r > 0 && !visited[r-1][c] && pixels[r-1][c]) ?
                    perimeterOf(r-1,c): 0) + ((c < rows-1 && !visited[r][c+1] &&
                    pixels[r][c+1]) ? perimeterOf(r,c+1) : 0) + ((c > 0 && !visited[r][c-1] && pixels[r][c-1]) ?
                    perimeterOf(r,c-1) : 0) + ((r < cols-1 && !pixels[r+1][c] || r == cols-1) ?
                    1: 0) + ((r > 0 && !pixels[r-1][c] || r == 0) ?
                    1: 0) + ((c < rows-1 && !pixels[r][c+1] || c == rows-1) ?
                    1 : 0) + ((c > 0 && !pixels[r][c-1] || c == 0) ?
                    1 : 0);



        if ((c+1 < pixels[r].length)||(c-1 >=0)){
            if ((pixels[r][c-1] == false)||(pixels[r][c+1] == false)) {
                if ((visited[r-1][c] != occupied) && (pixels[r-1][c] == true)) {
                    visited[r-1][c]=occupied;
                    count++;

                    perimeterOf(r-1, c );
                }
                if ((visited[r+1][c] != occupied) && (pixels[r+1][c] == true)) {
                    visited[r+1][c]=occupied;
                    count++;

                    perimeterOf(r+1, c );
                }
            }
        return count;

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
        ArrayList<String> list=new ArrayList<>();
        for (int r=0; r<pixels.length; r++) {
            String sub ="";
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                    sub += ("*");
                } else {
                    sub+= ("-");
                }
            }
            list.add(sub);
            }
        for (String a: list){
            System.out.println(a);
        }
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

             Byte[][] data = new Byte[list.size()][(list.get(0).length()+1)/2];
             for (int i=0; i<list.size(); i++) {
                 for (int j=0;j < list.get(i).length();j+=2) {
                     if ((list.get(i)).substring(j, j + 1) != null) {
                         data[i][j/2] = Byte.valueOf(((list.get(i)).substring(j, j + 1)));

                     }
                 }

             }
             return new PartImage(list.size(), (list.get(0).length()+1)/2,data);
         } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for reading");
        } catch (IOException e) {
            System.out.println("Error: Cannot read from file");
        }

        return null;    }
}
