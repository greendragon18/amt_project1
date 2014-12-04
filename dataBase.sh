#!/bin/bash
DB_NAME=AMTDatabase	
DB_TECHNICAL_USER=amtUser	
DB_TECHNICAL_USER_PASSWORD=1234

mysql -u root -p <<QUERY_INPUT

DROP DATABASE IF EXISTS $DB_NAME;	
CREATE DATABASE $DB_NAME;

GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'%';

DROP USER '$DB_TECHNICAL_USER'@'localhost';	
DROP USER '$DB_TECHNICAL_USER'@'%';

CREATE USER '$DB_TECHNICAL_USER'@'localhost' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';	
CREATE USER '$DB_TECHNICAL_USER'@'%' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';

GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'localhost';	  
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'%';

USE $DB_NAME;	
CREATE TABLE \`sensors\` (
  \`id\` int(11) NOT NULL AUTO_INCREMENT,	
  \`description\` tinytext NOT NULL, 	
  \`type\` tinytext NOT NULL,
  PRIMARY KEY (\`id\`),
  UNIQUE KEY \`id\` (\`id\`)	
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;	

INSERT INTO \`sensors\` (\`id\`, \`description\`, \`type\`) VALUES (NULL, 'ROOM_1', 'TEMPERATURE');
INSERT INTO	\`sensors\` (\`id\`, \`description\`, \`type\`) VALUES (NULL, 'ROOM_2', 'TEMPERATURE');
INSERT INTO	\`sensors\` (\`id\`, \`description\`, \`type\`) VALUES (NULL, 'ROOM_31', 'TEMPERATURE');
INSERT INTO	\`sensors\` (\`id\`, \`description\`, \`type\`) VALUES (NULL, 'CAR_12', 'SPEED');
INSERT INTO	\`sensors\` (\`id\`, \`description\`, \`type\`) VALUES (NULL, 'CAR_99', 'SPEED');
QUERY_INPUT



ASADMIN=/home/bradock/glassfish-4.0/glassfish/bin/asadmin

DOMAIN_NAME=domainAMT
JDBC_CONNECTION_POOL_NAME=${DB_NAME}_pull
JDBC_JNDI_NAME=jdbc/${DB_NAME}


$ASADMIN stop-domain $DOMAIN_NAME	  
$ASADMIN delete-domain $DOMAIN_NAME
$ASADMIN create-domain --nopassword=true $DOMAIN_NAME

cp mysql-connector-java-5.1.33-bin.jar /home/bradock/glassfish-4.0/glassfish/domains/$DOMAIN_NAME/lib

$ASADMIN start-domain $DOMAIN_NAME 

# Let's create a connection pool...
$ASADMIN create-jdbc-connection-pool \
  --restype=javax.sql.XADataSource \
  --datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
  --property User=$DB_TECHNICAL_USER:Password=$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=$DB_NAME $JDBC_CONNECTION_POOL_NAME

# ... check that it is properly setup...
$ASADMIN ping-connection-pool $JDBC_CONNECTION_POOL_NAME

# ... and finally create a jdbc resource mapped to our pool
$ASADMIN create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME


#$ASADMIN create-jdbc-connection-pool \
#	--restype=javax.sql.XADataSource \
#	--datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
#	--property User=$DB_TECHNICAL_USER:Password=
#$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=
#$DB_NAME $JDBC_CONNECTION_POOL_NAME
#$ASADMIN create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME
#$ASADMIN ping-connection-pool $JDBC_CONNECTION_POOL_NAME