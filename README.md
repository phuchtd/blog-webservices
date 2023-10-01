# Blog webservices - Using Springboot, Mysql, Docker

#### Setup mysql
    
    phuchoang@192 ~ % docker pull mysql
    ...
	phuchoang@192 ~ % docker images
	REPOSITORY    TAG       IMAGE ID       CREATED        SIZE
	mysql         latest    3503aa5f0751   2 days ago     599MB

#### Create network:

    phuchoang@192 ~ % docker network create springboot-mysql-net
    ...
	phuchoang@192 ~ % docker network ls                         
	NETWORK ID     NAME                   DRIVER    SCOPE
	7d55904c3a2d   bridge                 bridge    local
	bb45df6bae45   host                   host      local
	11ae5c5e97a2   none                   null      local
	0bb85fcc9656   springboot-mysql-net   bridge    local

	phuchoang@192 ~ % docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=blogdb -d mysql

    phuchoang@192 ~ % docker ps
    CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS                 NAMES
    0293376744df   mysql     "docker-entrypoint.s…"   9 seconds ago   Up 8 seconds   3306/tcp, 33060/tcp   mysqldb
    phuchoang@192 ~ % docker logs -f 0293376744df

    phuchoang@192 ~ % docker exec -it 0293376744df bash
    bash-4.4# mysql -u root -p
    Enter password:
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 8
    Server version: 8.1.0 MySQL Community Server - GPL

    Copyright (c) 2000, 2023, Oracle and/or its affiliates.
    
    Oracle is a registered trademark of Oracle Corporation and/or its
    affiliates. Other names may be trademarks of their respective
    owners.
    
    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

    mysql> show databases;
    +--------------------+
    | Database           |
    +--------------------+
    | blogdb             |
    | information_schema |
    | mysql              |
    | performance_schema |
    | sys                |
    +--------------------+
    5 rows in set (0.01 sec)



#### Option 1: Build and Run docker image

    cd code/blog
    docker build -t blog .
    docker run --network springboot-mysql-net --name blog-mysql-container -p 8080:8080 blog

    // run background
    docker run --network springboot-mysql-net --name  -p 8080:8080 -d blog

	phuchoang@192 blog % docker ps
	CONTAINER ID   IMAGE     COMMAND                  CREATED          STATUS          PORTS                    NAMES
	8ce0a5988271   blog      "java -jar blog-0.0.…"   4 seconds ago    Up 3 seconds    0.0.0.0:8080->8080/tcp   blog-mysql-container
	0293376744df   mysql     "docker-entrypoint.s…"   36 minutes ago   Up 36 minutes   3306/tcp, 33060/tcp      mysqldb

#### Option 2: Using docker-compose.yml

    cd code/blog
    docker-compose up -d --build    // start
    docker-compose down             // stop