import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CalculateAverage {

    private static class Stats {
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int count = 0;

        void add(double value) {
            sum += value;
            count++;
            if (value < min)
                min = value;
            if (value > max)
                max = value;
        }

        double getAvg() {
            final var value = (Math.round(sum * 10.0) / 10.0) / count;
            return Math.round(value * 10.0) / 10.0;
        }
    }

    public static void main(String[] args) {
        String filePath = "src/test/resources/measurements.txt";
        Map<String, Stats> stations = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 2)
                    continue;
                String station = parts[0];
                double temp = Double.parseDouble(parts[1]);

                stations.computeIfAbsent(station, k -> new Stats()).add(temp);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        boolean first = true;
        for (Map.Entry<String, Stats> entry : stations.entrySet()) {
            if (!first)
                System.out.print(", ");
            first = false;
            Stats s = entry.getValue();
            System.out.print(entry.getKey() + "=" + s.min + "/" + s.getAvg() + "/" + s.max);
        }
        System.out.println();
    }
}
