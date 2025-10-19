import java.io.*;
import java.nio.file.*;
import java.util.*;

//Sergi Pujol i Unai Clapers
public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static Map<Integer, Integer> reportsByYear = new HashMap<>();
    public static Map<Integer, Integer> deathsByYear = new HashMap<>();

    public static Map<String, Integer> deathsByQuarter = new LinkedHashMap<>();

    public static void main(String[] args) {
        Path filePath = Path.of(requestPath());
        readData(filePath);
        writeResults(filePath);
    }

    /**
     * Requests the file path from the user and checks if it exists.
     */
    private static String requestPath() {
        System.out.print("Enter the path to the CSV file: ");
        String path = sc.nextLine();
        if (!Files.exists(Path.of(path))) {
            System.err.println("Error: file not found.");
            System.exit(1);
        }
        return path;
    }

    /**
     * Reads the CSV file
     */
    private static void readData(Path file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
            String header = br.readLine(); // skip the header
            if (header == null || !header.contains("Year") && !header.contains("Any")) {
                throw new RuntimeException("Invalid file format.");
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int year = Integer.parseInt(data[0].trim());
                String quarter = data[1].trim();
                int reports = Integer.parseInt(data[4].trim());
                int deaths = Integer.parseInt(data[5].trim());

                reportsByYear.put(year, reportsByYear.getOrDefault(year, 0) + reports);
                deathsByYear.put(year, deathsByYear.getOrDefault(year, 0) + deaths);

                deathsByQuarter.put(quarter, deathsByQuarter.getOrDefault(quarter, 0) + deaths);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Error: invalid numeric value in file.");
            System.exit(1);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Writes results and graphs to results.txt in the same folder as the CSV.
     */
    private static void writeResults(Path originalFile) {
        Path resultPath = originalFile.getParent().resolve("results.txt");

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(resultPath))) {
            writer.println("=== Reports Statistics ===\n");

            // Total reports and mortal victims per year
            for (int year : reportsByYear.keySet()) {
                int reports = reportsByYear.get(year);
                int deaths = deathsByYear.getOrDefault(year, 0);
                writer.printf("%d  Total reports: %d  Mortal victims: %d%n", year, reports, deaths);
            }

            // Bar chart of reports per year (in % of the year with the most reports)
            writer.println("\nChart of reports per year (100% = year with most reports)\n");
            int maxReports = Collections.max(reportsByYear.values());
            for (int year : reportsByYear.keySet()) {
                int barLength = (int) ((reportsByYear.get(year) / (double) maxReports) * 100);
                writer.println(year);
                writer.println("|".repeat(barLength) + "\n");
            }

            // Bar chart of mortal victims per quarter (100% = quarter with most deaths)
            writer.println("Chart of mortal victims per quarter (100% = quarter with most deaths)\n");
            int maxDeaths = Collections.max(deathsByQuarter.values());
            for (String quarter : deathsByQuarter.keySet()) {
                int deaths = deathsByQuarter.getOrDefault(quarter, 0);
                int barLength = (int) ((deaths / (double) maxDeaths) * 100);
                writer.println(quarter + " Quarter");
                writer.println("|".repeat(barLength) + "\n");
            }

            System.out.println("Results successfully written to: " + resultPath);

        } catch (IOException e) {
            System.err.println("Error writing results file: " + e.getMessage());
        }
    }
}

/*
CONCLUSIONS:

- La quantitat de denúncies per any han augmentat a mesura que passa el temps, sent el 2024 on hi ha el registre més alt.
- Els anys amb més víctimes ateses no sempre coincideixen amb els anys amb més víctimes mortals.
- El segon trimestre és el que acumula més víctimes mortals, i el quart el que menys.

*/



