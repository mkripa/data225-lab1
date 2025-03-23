# data225-lab1

1. Connect to MySQL. 
   Run :
   drop database tmdb;
   create database tmdb;
   use tmdb;
   
2. Run dd.sql to create tables

3. Download the ZIP from https://drive.google.com/drive/folders/1iUlkCjs1KXc-u3F6yfGE4T7JbfX37IOT and unzip in the desired path

4. Configure MySQL url, username and password in configuration.properties
   Ex :
   mysql.url=jdbc:mysql://localhost:3306/tmdb
   mysql.user=abcd
   mysql.pass=abcd
   
5. Run from inside the TMDB directory
   java -cp dependencies/json.jar:dependencies/mysql-connector-java.jar:tmdb.jar Populate

tmdb.sql to import : [https://github.com/mkripa/data225-lab1/tree/main](https://drive.google.com/drive/u/0/folders/1iUlkCjs1KXc-u3F6yfGE4T7JbfX37IOT)
