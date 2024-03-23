import java.sql.*;
import java.util.*;

public class DataLoader {

	public static void loadCollections(Map<Long, MoviesMetadata> movieMap) {
		List<Collection> collectionList = new ArrayList<>();
		for (Long movieID : movieMap.keySet()) {
			MoviesMetadata metadata = movieMap.get(movieID);
			Collection collection = metadata.getBelongs_to_collection();
			collectionList.add(collection);
		}
		insertCollections(collectionList);
		System.out.println("Repeats = " + repeats);
	}

	private static final String INSERT_COLLECTION_SQL = "INSERT INTO collection (id, name, poster_path, backdrop_path) VALUES (?, ?, ?, ?)";
	private static final String INSERT_MOVIE_QUERY = "INSERT INTO Movie (tmdb_id, adult, budget, homepage, imdb_id, original_language, original_title, overview, popularity, poster_path, release_date, revenue, runtime, status, tagline, title, video, vote_average, vote_count, collection_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_PROD_COMPANIES_QUERY = "INSERT INTO ProductionCompany (id, name) VALUES (?, ?)";
	private static final String INSERT_PROD_COUNTRY_QUERY = "INSERT INTO ProductionCountry VALUES (?, ?)";
	private static final String INSERT_GENRE_QUERY = "INSERT INTO Genre VALUES (?, ?)";
	private static final String INSERT_SPOKEN_LANGUAGE_QUERY = "INSERT INTO SpokenLanguage VALUES (?, ?)";
	private static final String INSERT_KEY_VAL_PATTERN = "insert into %s values (?, ?)";



	private static int repeats = 0;
	public static void insertCollections(List<Collection> collections) {
		try {
			Connection connection = getConnection();
			for (Collection collection : collections) {
				if (collection == null) {
					continue;
				}
				if (!isRowExists(connection, "Collection", collection.getId())) {
					insertCollection(connection, collection);
				} else {
					repeats++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isRowExists(Connection connection, String tableName, Long id) throws SQLException {
		String select_sql = "SELECT id FROM " + tableName + " WHERE id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(select_sql)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	private static void insertCollection(Connection connection, Collection collection) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COLLECTION_SQL)) {
			preparedStatement.setLong(1, collection.getId());
			preparedStatement.setString(2, collection.getName());
			preparedStatement.setString(3, collection.getPoster_path());
			preparedStatement.setString(4, collection.getBackdrop_path());
			preparedStatement.executeUpdate();
		}
	}

	public static void loadMovies(Map<Long, MoviesMetadata> movieMap) {
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(INSERT_MOVIE_QUERY);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				pstmt.setLong(1, movie.getTmdb_id());
				pstmt.setBoolean(2, movie.isAdult());
				pstmt.setLong(3, movie.getBudget());
				pstmt.setString(4, movie.getHomepage());
				if (movie.getImdb_id() != null) {
					pstmt.setLong(5, movie.getImdb_id());
				} else {
					pstmt.setNull(5, Types.INTEGER);
				}
				pstmt.setString(6, movie.getOriginal_language());
				pstmt.setString(7, movie.getOriginal_title());
				pstmt.setString(8, movie.getOverview());
				pstmt.setFloat(9, movie.getPopularity());
				pstmt.setString(10, movie.getPoster_path());

				if (movie.getRelease_date() != null) {
					pstmt.setDate(11, new java.sql.Date(movie.getRelease_date().getTime()));
				} else {
					pstmt.setNull(11, java.sql.Types.DATE);
				}

				pstmt.setLong(12, movie.getRevenue());
				if (movie.getRuntime() != null) {
					pstmt.setInt(13, movie.getRuntime());
				} else {
					pstmt.setNull(13, java.sql.Types.INTEGER);
				}
				pstmt.setString(14, movie.getStatus());
				pstmt.setString(15, movie.getTagline());
				pstmt.setString(16, movie.getTitle());
				pstmt.setBoolean(17, movie.isVideo());
				pstmt.setFloat(18, movie.getVote_average());
				pstmt.setInt(19, movie.getVote_count());
				Collection c = movie.getBelongs_to_collection();
				if (c != null) {
					pstmt.setLong(20, movie.getBelongs_to_collection().getId());
				} else {
					pstmt.setNull(20, Types.INTEGER);
				}
				pstmt.addBatch();
			}

			int[] rowsInserted = pstmt.executeBatch();

			conn.commit();

			System.out.println("Inserted " + rowsInserted.length + " rows into the Movies table.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadProductionCompanies(Map<Long, MoviesMetadata> movieMap) {

		Map<Long, ProductionCompany> productionCompanyMap = new HashMap<>();
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(INSERT_PROD_COMPANIES_QUERY);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				for (ProductionCompany productionCompany : movie.getProduction_companies()) {
					productionCompanyMap.put(productionCompany.getId(), productionCompany);
				}
			}

			for (Long prodCompID : productionCompanyMap.keySet()) {
				pstmt.setLong(1, productionCompanyMap.get(prodCompID).getId());
				pstmt.setString(2, productionCompanyMap.get(prodCompID).getName());
				pstmt.addBatch();
			}

			// Execute the batch
			int[] batchResult = pstmt.executeBatch();
			System.out.println("Rows inserted: " + batchResult.length);
			conn.commit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadProductionCountries(Map<Long, MoviesMetadata> movieMap) {

		Map<String, ProductionCountry> productionCountryMap = new HashMap<>();
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(INSERT_PROD_COUNTRY_QUERY);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				for (ProductionCountry productionCountry : movie.getProduction_countries()) {
					productionCountryMap.put(productionCountry.getId(), productionCountry);
				}
			}

			for (String countryID : productionCountryMap.keySet()) {
				pstmt.setString(1, productionCountryMap.get(countryID).getId());
				pstmt.setString(2, productionCountryMap.get(countryID).getName());
				pstmt.addBatch();
			}

			// Execute the batch
			int[] batchResult = pstmt.executeBatch();
			System.out.println("Rows inserted: " + batchResult.length);
			conn.commit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadGenres(Map<Long, MoviesMetadata> movieMap) {

		Map<Long, Genre> genreMap = new HashMap<>();
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(INSERT_GENRE_QUERY);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				for (Genre genre : movie.getGenres()) {
					genreMap.put(genre.getId(), genre);
				}
			}

			for (Long prodCompID : genreMap.keySet()) {
				pstmt.setLong(1, genreMap.get(prodCompID).getId());
				pstmt.setString(2, genreMap.get(prodCompID).getName());
				pstmt.addBatch();
			}

			// Execute the batch
			int[] batchResult = pstmt.executeBatch();
			System.out.println("Rows inserted: " + batchResult.length);
			conn.commit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadSpokenLanguages(Map<Long, MoviesMetadata> movieMap) {

		Map<String, SpokenLanguage> spokenLanguageHashMap = new HashMap<>();
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(INSERT_SPOKEN_LANGUAGE_QUERY);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				for (SpokenLanguage spokenLanguage : movie.getSpoken_languages()) {
					spokenLanguageHashMap.put(spokenLanguage.getId(), spokenLanguage);
				}
			}

			for (String spLangID : spokenLanguageHashMap.keySet()) {
				pstmt.setString(1, spokenLanguageHashMap.get(spLangID).getId());
				pstmt.setString(2, spokenLanguageHashMap.get(spLangID).getName());
				pstmt.addBatch();
			}

			int[] batchResult = pstmt.executeBatch();
			System.out.println("Rows inserted: " + batchResult.length);
			conn.commit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void linkTablesToMovies(Map<Long, MoviesMetadata> movieMap, String tableName) {
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			System.out.println("\n"+tableName);
			String query = String.format(INSERT_KEY_VAL_PATTERN, tableName);
			PreparedStatement pstmt = conn.prepareStatement(query);

			for (Long movieID : movieMap.keySet()) {
				MoviesMetadata movie = movieMap.get(movieID);
				Set<String> strValSet = new HashSet<>();
				if (tableName.equals("MovieToProductionCountry") || (tableName.equals("MovieToSpokenLanguage"))) {
					if (tableName.equals("MovieToProductionCountry")) {
						for (ProductionCountry productionCountry : movie.getProduction_countries()) {
							strValSet.add(productionCountry.getId());
						}
					} else {
						for (SpokenLanguage spokenLanguage : movie.getSpoken_languages()) {
							strValSet.add(spokenLanguage.getId());
						}
					}
						for (String value : strValSet) {
							pstmt.setLong(1, movieID);
							pstmt.setString(2, value);
							pstmt.addBatch();
						}
					} else {
					Set<Long> valueSet = new HashSet<>();
						if (tableName.equals("MovieToProductionCompany")) {
							for (ProductionCompany productionCompany : movie.getProduction_companies()) {
								valueSet.add(productionCompany.getId());
							}
						} else if (tableName.equals("MovieToGenre")) {
							for (Genre genre : movie.getGenres()) {
								valueSet.add(genre.getId());
							}
						}
					for (Long value : valueSet) {
						pstmt.setLong(1, movieID);
						pstmt.setLong(2, value);
						pstmt.addBatch();
					}

				}


			}

			int[] batchResult = pstmt.executeBatch();
			System.out.println("Rows inserted: " + batchResult.length);
			conn.commit();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(ConfigurationLoader.configuration.get("mysql.url"), ConfigurationLoader.configuration.get("mysql.user"), ConfigurationLoader.configuration.get("mysql.pass"));
	}

}
