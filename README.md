# PMT

Currently applicable properties are as follows. 

# H2 Database support
#spring.datasource.url = jdbc:h2:file:${loader.db-file:${user.dir}/dev/db/put-dev};FILE_LOCK=FS
#spring.datasource.driverClassName = org.h2.Driver
#spring.datasource.username = ${loader.db-username:admin}
#spring.datasource.password = ${loader.db-password:penurtrip}
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Mysql support
spring.datasource.url = jdbc:mysql://localhost:3306/<schemaname>
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.datasource.username = <username>
spring.datasource.password = <pwd>
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
# Specify the DBMS
spring.jpa.database = MYSQL

# Show or not log for each sql query
spring.jpa.show-sql = true

# Hibernate settings are prefixed with spring.jpa.hibernate.*
spring.jpa.hibernate.ddl-auto = create-drop
spring.jpa.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.jpa.hibernate.naming_strategy = org.hibernate.cfg.ImprovedNamingStrategy
