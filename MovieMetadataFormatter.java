import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MovieMetadataFormatter {

	public static Map<Long, MoviesMetadata> getMovieMetaData() throws Exception {

	//	String TSV_FILE = "/Users/kripa/Desktop/Kripa/SJSU/225/Lab1/datasets/movies_metadata.tsv";
		String TSV_FILE = System.getProperty("user.dir") + File.separator + "dependencies" + File.separator + "movies_metadata.tsv";
		String line = "";

		Map<Long, MoviesMetadata> moviesMetadataMap = new HashMap<>();
		String s = "";
		boolean isFirstLine = true;

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

		BufferedReader br = new BufferedReader(new FileReader(TSV_FILE));
		while ((line = br.readLine()) != null) {
			if (isFirstLine) {
				isFirstLine = false;
				continue;
			}
			String[] fields = line.split("\t");
			if (fields.length < 23) {
				continue;
			}
			MoviesMetadata m = new MoviesMetadata();
			m.setAdult(Boolean.parseBoolean(fields[0]));

			if (fields[1].isEmpty()) {
				m.setBelongs_to_collection(null);
			} else {
				JSONObject jsonObject = new JSONObject(fields[1]);
				m.setBelongs_to_collection_json(jsonObject);
			}
			m.setBudget(Long.parseLong(fields[2]));

			JSONArray jsonArray = new JSONArray(fields[3]);
			m.setGenres(jsonArray);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.optJSONObject(i); 	//del
			}

			m.setHomepage(fields[4]);
			m.setTmdb_id(Long.parseLong(fields[5]));
			if (fields[6].equals("")) {
				m.setImdb_id(null);
			} else {
				m.setImdb_id(Long.parseLong(fields[6].replaceAll("tt", "")));
			}
			m.setOriginal_language(fields[7]);
			m.setOriginal_title(fields[8]);
			m.setOverview(fields[9]);
			m.setPopularity(Float.parseFloat(fields[10]));

			m.setPoster_path(fields[11]);

			try {
				JSONArray prodCompJsonArray = new JSONArray(fields[12]);
				m.setProduction_companies(prodCompJsonArray);

			} catch (JSONException e) {
				s = fields[12].replaceAll("\\\\xa0", "");
				JSONArray prodCompJsonArray = new JSONArray(s);
				m.setProduction_companies(prodCompJsonArray);
			}
			JSONArray prodCountryJsonArray = new JSONArray(fields[13]);

			m.setProduction_countries(prodCountryJsonArray);
			if (fields[14].equals("")) {
				m.setRelease_date(null);
			}
			else {
				try {
					m.setRelease_date(dateFormat.parse(fields[14]));
				} catch (ParseException pe) {
					SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
					m.setRelease_date(dateFor.parse(fields[14]));
				}
			}
			m.setRevenue(Long.parseLong(fields[15]));
			Integer runtime = fields[16].isEmpty() ? null : Integer.parseInt(fields[16]);
			m.setRuntime(runtime);

			try {
				JSONArray splJsonArray = new JSONArray(fields[17]);
				m.setSpoken_languages(splJsonArray);

			} catch (JSONException e) {
				s = fields[12].replaceAll("[^\\x00-\\x7F]", "");
				s = s.replaceAll("\\\\xa0", "");
				JSONArray splJsonArray = new JSONArray(s);
				m.setSpoken_languages(splJsonArray);
			}
			m.setStatus(fields[18]);
			m.setTagline(fields[19]);
			m.setTitle(fields[20]);
			m.setVideo(Boolean.parseBoolean(fields[21]));
			m.setVote_average(Float.parseFloat(fields[22]));
			m.setVote_count(Integer.parseInt(fields[23]));

			moviesMetadataMap.put(m.getTmdb_id(), m);
		}
		return moviesMetadataMap;

	}

}

