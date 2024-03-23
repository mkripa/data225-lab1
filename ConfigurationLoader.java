import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationLoader {
	static String filename = "configuration.properties";
	public static Map<String, String> configuration = readKeyValueFile(filename);

	public static void load() {
		for (Map.Entry<String, String> entry : configuration.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

	public static Map<String, String> readKeyValueFile(String filename) {
		Map<String, String> configuration = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("=", 2);
				if (parts.length == 2) {
					String key = parts[0].trim();
					String value = parts[1].trim();
					configuration.put(key, value);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}

		return configuration;
	}
}
