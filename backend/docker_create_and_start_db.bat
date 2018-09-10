docker stop test-mysql
docker rm test-mysql
docker run -p 3306:3306 --name test-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql:5.7