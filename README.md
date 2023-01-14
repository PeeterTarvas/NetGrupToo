# NetGroupToo

#### Small warning
 - Front-end in front directory uses port 3000
 - Back-end in NetGroupToo directory uses port 8000
 - PostgreSql should be on port 8080, all the sql generating scripts are in NetGroupToo/src/main/resources
   ,there is also a data.sql file in main directory which generates the whole database if needed.
 - In front-end if products/directories don't load after their creation use `Reload products and catalogues`
   button to reload the page

## Running locally
 - Need to have deps installed:
   - #### Java 17(minimum)
   - #### Node 16.18.10(minimum)
   - #### PostgresSQL 15.1
 - Start PostgreSQL on port 8080 with username: postgres, password: postgres, can be done inside IntelliJ idea easily
 - PostgreSQL database scripts will trigger automatically when app is started
 - Run `gradle build` in NetGroupToo directory(ik I misspelled Group in the application main class )
   - If there is a problem that the tests won't go through, because test run doesn't have database connection,
   so you can skip tests with the flag `-x test`
   - Then run jar file `java -jar NetGroupToo/build/libs/NetGrupToo-0.0.1-SNAPSHOT.jar`
 - Run in front directory:  
   - `npm install --silent`
   - `npm install react-scripts@5.0.1 -g --silent`
   - `npm start`

## Running with docker-compose
  - Need to have Docker installed and running
  - Setup:
    Run `gradle build` inside the NetGroupToo folder, this will generate the jar file needed to run docker-compose.yml
  - Run `docker-compose up -d`
  - Check docker container cluster for logs if needed
  - Wait a bit since the applications inside the container need some time to start
  - If front-end has compiled then go to `localhost:3000` to see the application
