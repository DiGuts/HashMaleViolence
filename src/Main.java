import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static HashMap<String, Integer> reportsByYear = new HashMap<>();
    public static HashMap<String, Integer> deathsByYear = new HashMap<>();

    public static void main(String[] args) {


        readData(Path.of(requestPath()));

        writeResults();
    }

    private static void readData(Path file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
            String line = br.readLine();
            while(line= br.readLine()!=null){
                String[] data = line.split(",");
                if(reportsByYear.containsKey(String.valueOf(data[0]))){
                    reportsByYear.put(String.valueOf(data[0]), reportsByYear.get(data[0]) + Integer.parseInt(data[4]));
                    deathsByYear.put(String.valueOf(data[0]), reportsByYear.get(data[0]) + Integer.parseInt(data[5]));
                } else {
                    reportsByYear.put(String.valueOf(data[0]), Integer.parseInt(data[4]));
                    deathsByYear.put(String.valueOf(data[0]), Integer.parseInt(data[5]));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String requestPath() {
        System.out.println("Enter the path to the file you want to read:");
        String path = sc.nextLine();
        if (!Files.exists(Path.of(path))){
            throw new RuntimeException("File not found");
        }
        return path;

    }

    private static void writeResults(){
        int maxDeaths = 0;
        for (Map.Entry<String, Integer> entrada : reportsByYear.entrySet()) {
            String any = entrada.getKey();
            int reports = entrada.getValue();
            int deaths = deathsByYear.get(any);
            System.out.println(any + "  Denúncies totals: " + reports + "  Victimes mortals:" + deaths);
            //Escriure resultats al fitxer
        }

        //Saves the most deaths in a year in maxDeaths
        for (Map.Entry<String, Integer> entrada : reportsByYear.entrySet()) {
            if (reportsByYear.get(entrada.getKey()) > maxDeaths){
                maxDeaths = reportsByYear.get(entrada.getKey());
            }
        }

        System.out.println("Victims graph");
        for (Map.Entry<String, Integer> entrada : reportsByYear.entrySet()) {
            int stickNum = entrada.getValue()/maxDeaths * 100;
            System.out.println(entrada.getKey() + "  Victimes mortals: ");
            for (int i = 0; i < stickNum; i++) System.out.print("|");
        }

    }
}