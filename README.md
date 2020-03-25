# BestBelarusPlacesToVisitBot

#### The Bot suggests some places to visit in Belarus cities.
Bot name:<br/>
[BestBelarusPlacesToVisitBot](https://t.me/BestBelarusPlacesToVisitBot)<br/>
Bot token:<br/>
1092447812:AAEDu9aBbLI2aQPNZYIDezskwt5lKBrDaeg<br/>
**Tools:**<br/>

 1. Java 8
 2. PostgreSQL 10-12 (+ pgAdmin 4)
 3. Maven 3.6 
## INSTRUCTION
 1. Create a database with the name "travel_belarus":<br/>


    CREATE DATABASE travel_belarus
            WITH 
            OWNER = postgres
            ENCODING = 'UTF8'
            LC_COLLATE = 'en_US.UTF-8'
            LC_CTYPE = 'en_US.UTF-8'
            TABLESPACE = pg_default
            CONNECTION LIMIT = -1;


 2. Create a database and tables with names "city" and "city_place":<br/>
 

    CREATE TABLE city
        (
        city_id SERIAL,
        name VARCHAR(40) NOT NULL UNIQUE,
        CONSTRAINT city_pk PRIMARY KEY(city_id)
    );

    CREATE TABLE city_place
        (
        place_id SERIAL,
        city_id INT,
        info VARCHAR(200) NOT NULL,
        CONSTRAINT place_pk PRIMARY KEY(place_id),
        CONSTRAINT place_to_city_fk FOREIGN KEY (city_id)    REFERENCES city (city_id)
    );

3. Configure Spring boot using a properties file 
[/src/main/resources/application.properties](/src/main/resources/application.properties).<br/>
- the data source: 

    jdbc.url=jdbc:postgresql://127.0.0.1:5432/travel_belarus<br/>
    jdbc.username=postgres<br/>
    jdbc.password=123<br/>

- the max amount of city places in a bot message (***one and more***):

    city.places=2<br/>

4.  Launch an application from the command line:


    mvn spring-boot:run<br/>

5. Open  [Springfox's Swagger UI](http://localhost:8080/swagger-ui.html) in a browser.
6. Using this UI create a city ("*Minsk*"), create some city places for the city (*"Visit Sts. Simon and Helena Church, or Red Church", "Walk the central part of Nezavisimosti Avenue", "Visit the Upper Town", "See the grandeur of Bolshoi Opera and Ballet Theatre of Belarus"*), and add these places to the city.
7. Test this appliaction in **Telegram** [BestBelarusPlacesToVisitBot](https://t.me/BestBelarusPlacesToVisitBot).
8. When finish testing stop the app.