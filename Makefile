MYSQL_CONTAINER = mysql-db
MYSQL_VOLUME 	= mysql-data
MYSQL_PORT 		= 3306
MYSQL_IMAGE 	= mysql:8.3
MYSQL_ROOT_PWD 	= my-secret-pw
MYSQL_DB		= pagination-db

.PHONY: start stop restart logs status down shell mysql-users mysql-dbs mysql-connections
.PHONY: mysql-connections-full mysql-connections-by-user clean mysql-tables-info

start:
	@docker volume inspect $(MYSQL_VOLUME) >/dev/null 2>&1 || docker volume create $(MYSQL_VOLUME)
	@docker inspect $(MYSQL_CONTAINER) >/dev/null 2>&1 || \
	docker run -d \
		--name $(MYSQL_CONTAINER) \
		-e MYSQL_ROOT_PASSWORD=$(MYSQL_ROOT_PWD) \
		-p $(MYSQL_PORT):3306 \
		-v $(MYSQL_VOLUME):/var/lib/mysql \
		-v $(PWD)/db-scripts:/docker-entrypoint-initdb.d \
        $(MYSQL_IMAGE)
	@docker start $(MYSQL_CONTAINER)

stop:
	@docker stop $(MYSQL_CONTAINER)

restart: stop start

logs:
	@docker logs -f $(MYSQL_CONTAINER)

status:
	@docker ps -a | grep $(MYSQL_CONTAINER) || true

down:
	@docker stop $(MYSQL_CONTAINER)
	@echo "Container stopped but NOT removed"

shell:
	@docker exec -it $(MYSQL_CONTAINER) mysql -u root -p$(MYSQL_ROOT_PWD)

mysql-users:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SELECT user, host FROM mysql.user;"

mysql-dbs:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SHOW DATABASES;"

mysql-connections:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SHOW STATUS LIKE 'Threads_connected';"

mysql-connections-full:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SELECT COUNT(*) AS connections FROM information_schema.PROCESSLIST;"

mysql-connections-by-user:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SELECT USER, COUNT(*) AS connections FROM information_schema.PROCESSLIST GROUP BY USER;"

mysql-tables-info:
	@docker exec -i $(MYSQL_CONTAINER) \
	mysql -u root -p$(MYSQL_ROOT_PWD) \
	-e "SELECT table_name FROM information_schema.tables WHERE table_schema='$(MYSQL_DB)';"

clean:
	@docker stop $(MYSQL_CONTAINER) || true
	@docker rm $(MYSQL_CONTAINER) || true
	@docker volume rm $(MYSQL_VOLUME) || true
