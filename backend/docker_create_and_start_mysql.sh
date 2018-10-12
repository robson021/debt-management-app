#!/usr/bin/env bash
docker run -p 3306:3306 --name test-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql:5.7