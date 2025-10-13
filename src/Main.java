import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static HashMap<String, Integer> reportsByYear = new HashMap<>();
    public static HashMap<String, Integer> deadByYear = new HashMap<>();

    public static void main(String[] args) {
        requestPath();

//        readData();

        writeResults();
    }

//    private static HashMap readData(Path file) {
//        try(BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
//            String line;
//            while(line= br.readLine()!=null){
//
//            }
//        }
//        return HashMap m = new  Has
//    }

    private static void requestPath() {
        System.out.println("Enter the path to the file you want to read:");
        String path = sc.nextLine();


    }

    private static void writeResults(){

        for (Map.Entry<String, Integer> entrada : reportsByYear.entrySet()) {
            String any = entrada.getKey();
            int reports = entrada.getValue();
            int deads = deadByYear.get(any);
            System.out.println(any + "  Denúncies totals: " + reports + "  Victimes mortals:" + deads);
        }
    }
}