import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class KeywordFormatter {
	static final String INSERT_KEYWORD_SQL = "INSERT INTO Keyword (id, name) VALUES (?, ?)";
	public static void main(String args[]) throws Exception {
		String TSV_FILE = System.getProperty("user.dir") + File.separator + "dependencies" + File.separator + "keywords.tsv";
		String line = "";
		String s = "";
		boolean isFirstLine = true;
		Map<Long, String> keywordMap = new HashMap();

		try (BufferedReader br = new BufferedReader(new FileReader(TSV_FILE))) {
			while ((line = br.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				String[] fields = line.split("\t");
				Keyword k = new Keyword();
				s = fields[1].replaceAll("\\\\xa0", "");
				JSONArray jsonArray = new JSONArray(s);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					keywordMap.putIfAbsent(jsonObject.optLong("id"), jsonObject.optString("name"));

				}
			}
			populateKeywordTable(keywordMap);

		}
	}

	private static void populateKeywordTable(Map<Long, String> keywordMap) throws Exception {
		try (Connection connection = DataLoader.getConnection()) {
			for (Long id : keywordMap.keySet()) {
				if (!DataLoader.isRowExists(connection, "Keyword", id)) {
					try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_KEYWORD_SQL)) {
						preparedStatement.setLong(1, id);
						preparedStatement.setString(2, keywordMap.get(id));
						preparedStatement.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

