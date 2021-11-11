<h1>lecture-15</h1>

<p>Home task lecture 15:</p>
<ol>
<li>Home task for 14.</li>
<li>Add mock test for jdbctemplate dao.</li>
<li>Add mock test for datasource dao.</li>
</ol>


<h2>What's done:<h2>
<ol>
<li>Added mock JdbcTemplate in ProducersDAO</li>
<li>Added tests for ProducersDAO</li>
<li>Added mock NamedParameterJdbcTemplate in GoodsDAOTests</li>
<li>Added tests for GoodsDAO</li>
<li>Added Liquibase support for CategoriesDAOTests to create tables and insert data when building project phase running</li>
<li>Moved docker-compose file to project root directory. Added docker-compose file for tests</li>
</ol>

<h3>To run tests and App you should:</h3>
<ol>
<li>Start postgresql server to make tests: $docker-compose -f docker-compose-test.yml up -d</li>
<li>Build project with maven (it's starting tests with liquibase support): $mvn clean package</li>
<li>To find started container id: $docker ps -a</li>
<li>Stop postgresql server: $docker stop [container_id]</li>
<li>Delete container: $docker rm [container_id]</li>
<li>Run new postgresql server for the App: $docker-compose -f docker-compose.yml up -d</li>
<li>Run liquibase to create tables and insert data: $mvn liquibase:update</li>
<li>Run App</li>
</ol>