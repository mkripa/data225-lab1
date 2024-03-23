import java.util.Map;

public class Populate {

	public static void main(String args[]) throws Exception {

	//	ColdStart.createTables();

		Map<Long, MoviesMetadata> moviesMetadataMap = MovieMetadataFormatter.getMovieMetaData();

		DataLoader.loadCollections(moviesMetadataMap);
		DataLoader.loadMovies(moviesMetadataMap);
		System.out.println("\nProductionCompanies");
		DataLoader.loadProductionCompanies(moviesMetadataMap);
		System.out.println("\nProductionCountries");
		DataLoader.loadProductionCountries(moviesMetadataMap);
		System.out.println("\nGenres");
		DataLoader.loadGenres(moviesMetadataMap);
		System.out.println("\nSpokenLanguages");
		DataLoader.loadSpokenLanguages(moviesMetadataMap);

		DataLoader.linkTablesToMovies(moviesMetadataMap, "MovieToProductionCountry");
		DataLoader.linkTablesToMovies(moviesMetadataMap, "MovieToProductionCompany");
		DataLoader.linkTablesToMovies(moviesMetadataMap, "MovieToGenre");
		DataLoader.linkTablesToMovies(moviesMetadataMap, "MovieToSpokenLanguage");
		//	DataLoader.linkKeywordsToMovies();

	}
}
