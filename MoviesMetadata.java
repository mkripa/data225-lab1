

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoviesMetadata {
	boolean adult;
	Collection belongs_to_collection;
	long budget;
	List<Genre> genres;
	String homepage;
	long tmdb_id;
	Long imdb_id;
	String original_language;
	String original_title;
	String overview;
	float popularity;
	String poster_path;
	List<ProductionCompany> production_companies;
	List<ProductionCountry> production_countries;
	Date release_date;
	long revenue;
	Integer runtime;
	List<SpokenLanguage> spoken_languages;
	String status;
	String tagline;
	String title;
	boolean video;
	float vote_average;
	int vote_count;

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public long getTmdb_id() {
		return tmdb_id;
	}

	public void setTmdb_id(long tmdb_id) {
		this.tmdb_id = tmdb_id;
	}

	public Long getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(Long imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public float getPopularity() {
		return popularity;
	}

	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public float getVote_average() {
		return vote_average;
	}

	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}

	public Collection getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(Collection belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public void setBelongs_to_collection_json(JSONObject jsonCollection) {
		Collection collection = new Collection();
		collection.setId(jsonCollection.getLong("id"));
		collection.setName(jsonCollection.getString("name"));
		collection.setBackdrop_path(jsonCollection.getString("backdrop_path"));
		collection.setPoster_path(jsonCollection.getString("poster_path"));
		this.belongs_to_collection = collection;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public void setGenres(JSONArray jsonArrGenres) {
		List<Genre> genreList = new ArrayList<>();
		for (int i = 0; i < jsonArrGenres.length(); i++) {
			JSONObject jsonObject = jsonArrGenres.optJSONObject(i);
			Genre genre = new Genre();
			genre.setId(jsonObject.getLong("id"));
			genre.setName(jsonObject.getString("name"));
			genreList.add(genre);
		}
		this.genres = genreList;
	}

	public void setProduction_companies(JSONArray jsonArrayProdCompanies) {
		List<ProductionCompany> productionCompanyList = new ArrayList<>();
		for (int i = 0; i < jsonArrayProdCompanies.length(); i++) {
			JSONObject jsonObject = jsonArrayProdCompanies.optJSONObject(i);
			ProductionCompany productionCompany = new ProductionCompany();
			productionCompany.setId(jsonObject.getLong("id"));
			productionCompany.setName(jsonObject.getString("name"));
			productionCompanyList.add(productionCompany);
		}
		this.production_companies = productionCompanyList;
	}

	public List<ProductionCompany> getProduction_companies() {
		return production_companies;
	}


	public void setProduction_companies(List<ProductionCompany> production_companies) {
		this.production_companies = production_companies;
	}

	public List<ProductionCountry> getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(List<ProductionCountry> production_countries) {
		this.production_countries = production_countries;
	}

	public void setProduction_countries(JSONArray jsonArrayProdCountries) {
		List<ProductionCountry> productionCountryList = new ArrayList<>();
		for (int i = 0; i < jsonArrayProdCountries.length(); i++) {
			JSONObject jsonObject = jsonArrayProdCountries.optJSONObject(i);
			ProductionCountry productionCountry = new ProductionCountry();
			productionCountry.setId(jsonObject.optString("iso_3166_1"));
			productionCountry.setName(jsonObject.optString("name"));
			productionCountryList.add(productionCountry);
		}
		this.production_countries = productionCountryList;
	}

	public List<SpokenLanguage> getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(List<SpokenLanguage> spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public void setSpoken_languages(JSONArray jsonArraySpLang) {
		List<SpokenLanguage> spokenLanguageList = new ArrayList<>();
		for (int i = 0; i < jsonArraySpLang.length(); i++) {
			JSONObject jsonObject = jsonArraySpLang.optJSONObject(i);
			SpokenLanguage spokenLanguage = new SpokenLanguage();
			spokenLanguage.setId(jsonObject.optString("iso_639_1"));
			spokenLanguage.setName(jsonObject.getString("name"));
			spokenLanguageList.add(spokenLanguage);
		}
		this.spoken_languages = spokenLanguageList;
	}
}
