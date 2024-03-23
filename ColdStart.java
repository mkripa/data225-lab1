import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class ColdStart {
	public static void createTables() {
		String sqlFilePath = "dd.sql";
		ConfigurationLoader.load();

		try (Connection connection = DataLoader.getConnection()) {
			Statement statement = connection.createStatement();

			StringBuilder sql = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
				String line;
				while ((line = reader.readLine()) != null) {
					sql.append(line).append("\n");
				}
			}

			boolean hasResults = statement.execute(sql.toString());
			if (!hasResults) {
				System.out.println("SQL commands executed successfully.");
			} else {
				System.out.println("SQL commands executed successfully. ResultSet returned.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
