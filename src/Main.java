import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        requestPath();

        readData();

        writeResults();
    }

    private static void readData(Path file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
            String line;
            while(line= br.readLine()!=null){

            }
        }
    }

    private static void requestPath() {
        System.out.println("Enter the path to the file you want to read:");
        String path = sc.nextLine();


    }
}