package com.travel.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("com.travel.model")
@EnableJpaRepositories(basePackages = "com.travel.repositories")
@EnableTransactionManagement
public class PersistenceConfig {
	// Hibernate Config properties
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_GENERATE_STATISTICS = "hibernate.generate_statistics";

	public static final String CITY_PICS_DIR = "data/cityPics";
	public static final String ATTRACTION_PICS_DIR = "data/attractionPics";

	/**
	 * Configures a file based datastore for storing large objects (tracks and
	 * biking pictures)
	 *
	 * @param datastoreBaseDirectoryPath
	 * @return
	 */
	@Bean
	public File datastoreBaseDirectory(
			final @Value("${travel-ds-base-directory:${user.dir}/dev}") String datastoreBaseDirectoryPath) {
		final File baseDsDir = new File(datastoreBaseDirectoryPath);
		if (!(baseDsDir.isDirectory() || baseDsDir.mkdirs())) {
			throw new RuntimeException(
					String.format(
							"Could not initialize '%s' as base directory for datastore!",
							baseDsDir.getAbsolutePath()));
		}
		new File(baseDsDir, CITY_PICS_DIR).mkdirs();
		new File(baseDsDir, ATTRACTION_PICS_DIR).mkdirs();
		return baseDsDir;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter(final Environment environment) {
		final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		// jpaVendorAdapter.setDatabase(Database.H2);
		// jpaVendorAdapter.setDatabasePlatform(H2Dialect.class.getName());
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform(MySQL5Dialect.class.getName());
		jpaVendorAdapter.setGenerateDdl(environment.acceptsProfiles("dev"));
		jpaVendorAdapter.setShowSql(environment.acceptsProfiles("dev", "test"));
		return jpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory(
			final Environment environment, final DataSource dataSource,
			final JpaVendorAdapter jpaVendorAdapter) {
		final Map<String, String> properties = new HashMap<>();
		properties.put(HIBERNATE_GENERATE_STATISTICS, "false");
		if (environment.acceptsProfiles("dev")) {
			properties.put(HIBERNATE_HBM2DDL_AUTO, "create");
		}
		final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName("com.travel.model_resourceLocale");
		// @EntityScan can also be used
		emf.setPackagesToScan("com.travel.model");
		emf.setJpaDialect(new HibernateJpaDialect());
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setDataSource(dataSource);
		emf.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		emf.setJpaPropertyMap(properties);
		return emf;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}
