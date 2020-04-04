import java.io.*;
import java.util.ArrayList;
 import java.util.Scanner; // Import the Scanner class to read text files

public class PartImage {
    private boolean[][]	pixels;
    private boolean[][]	visited;
    private int	rows;
    private int	cols;
    int count=0;

    public PartImage(int r, int c) {
        rows = r;
        cols = c;
        visited = new boolean[r][c];
        pixels = new boolean[r][c];
    }

    public PartImage(int rw, int cl, Byte[][] data) {
        this(rw, cl);
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                if (data[r][c] !=null){
                    if (data[r][c] == 1) {
                         pixels[r][c] = true;
                    } else {
                         pixels[r][c] = false;
                    } } } } }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean getPixel(int r, int c) { return pixels[r][c]; }

    public Point2D findStart() {
        for (int r=0; r<pixels.length; r++) {
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                    return new Point2D(r,c);
                } } }
        return null;
    }

    public int partSize() {
        int count = 0;
        for (int r=0; r<pixels.length; r++) {
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                    count++;
                } } }
        return count; }

    private void expandFrom(int r, int c) {
        pixels[r][c]=false;
        if (r+1 < pixels.length){
            if (pixels[r+1][c] == true) {
                 expandFrom(r + 1, c);
            } }
        if (r-1 >=0){
            if (pixels[r-1][c] == true) {
                  expandFrom(r - 1, c);
            } }
        if (c-1 >=0){
            if (pixels[r][c-1] == true) {
                  expandFrom(r , c-1);
            } }
        if (c+1 < pixels[r].length){
            if (pixels[r][c+1] == true) {
                  expandFrom(r, c+1);
            } } }

    private int perimeterOf(int r, int c) {
        boolean occupied = true;
        visited[r][c] = occupied;
        if (r + 1 < pixels.length) {
            if ((pixels[r + 1][c] == true)&&(visited[r+1][c]!=occupied)) {
                perimeterOf(r + 1, c);
            }else if((pixels[r + 1][c] == false)){
                count++;
            }
        }else{
            count++;
        }
        if (r - 1 >= 0) {
            if ((pixels[r - 1][c] == true)&&(visited[r-1][c]!=occupied)) {
                perimeterOf(r - 1, c);
            }else if(pixels[r - 1][c] == false){
                count++;
            }
        }else{
            count++;
        }
        if (c - 1 >= 0) {
            if ((pixels[r][c - 1] == true)&&(visited[r][c-1]!=occupied)) {
                perimeterOf(r, c - 1);
            }else if(pixels[r][c-1] == false){
                count++;
            }
        }else{
            count++;
        }
        if (c + 1 < pixels[r].length) {
            if ((pixels[r][c + 1] == true)&&(visited[r][c+1]!=occupied)) {
                perimeterOf(r, c + 1);
            }else if(pixels[r][c+1] == false){
                count++;
            }
        }else{
            count++;
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
        int pieces= 0;
        while (partSize() != 0) {
            expandFrom((int) findStart().getX(), (int) findStart().getY());
            pieces+=1;
            if (partSize() == 0){
                break;
            } }
        return pieces;
    }

    public void print() {
        //make a list and string to add all the characters
        ArrayList<String> list=new ArrayList<>();
        for (int r=0; r<pixels.length; r++) {
            String sub ="";
            for (int c=0; c<pixels[r].length; c++) {
                if (pixels[r][c] == true) {
                    sub += ("*");
                } else {
                    sub+= ("-");
                } }
            list.add(sub);
            }
        //print out what was add from each sublist
        for (String a: list){
            System.out.println(a);
        }     }

    public static PartImage readFromFile(String fileName) throws InvalidPartImageException{
         try {
             //filepath and opening of the file
              File file = new File(System.getProperty("user.dir")+"/"+fileName);

             //looping through the file lines
             Scanner checkLength = new Scanner(file);
             int length =  checkLength.nextLine().length();
             //check if file line length is the same as each other
             while (checkLength.hasNextLine()) {
                 if (checkLength.nextLine().length()!=length){
                     throw new InvalidPartImageException(fileName);
                 } }

             //add the file lines to a list so we can check the elements substring by substring
             Scanner checkNumbers = new Scanner(file);
             ArrayList<String> list=new ArrayList<>();
             while (checkNumbers.hasNextLine()) {
                 list.add(String.valueOf(checkNumbers.nextLine()));
             }
             Byte[][] data = new Byte[list.size()][(list.get(0).length()+1)/2];
             //we loop through the numbers ignoring the commas to see if there are non 1 and 0s
             //loop will also add the 0 and 1 to the data[][] array
             for (int i=0; i<list.size(); i++) {
                 for (int j=0;j < list.get(i).length();j+=2){
                     //data is add if not null
                     if ((list.get(i)).substring(j, j + 1) != null) {
                         data[i][j/2] = Byte.valueOf(((list.get(i)).substring(j, j + 1)));
                     }
                     //confirm there are only 1/0
                     if(!(list.get(i)).substring(j,j+1).contains("1")) {
                         if(!(list.get(i)).substring(j,j+1).contains("0")) {
                             throw new InvalidPartImageException(fileName);
                         } } } }
         //return the data once everything is good
         return new PartImage(list.size(), (list.get(0).length()+1)/2,data);
         } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file for reading");
        } catch (IOException e) {
            System.out.println("Error: Cannot read from file"); }
        return null;    }   }
