package hiber.config;

import hiber.model.Car;
import hiber.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "hiber")
public class AppConfig {
   private static final Logger logger = (Logger) LogManager.getLogger(AppConfig.class);

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      logger.info("Initializing DataSource");
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      try {
         dataSource.setDriverClassName(env.getProperty("db.driver"));
         dataSource.setUrl(env.getProperty("db.url"));
         dataSource.setUsername(env.getProperty("db.username"));
         dataSource.setPassword(env.getProperty("db.password"));
         logger.info("DataSource initialized successfully");
      } catch (Exception e) {
         logger.error("Error initializing DataSource", e);
      }
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      logger.info("Initializing SessionFactory");
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      try {
         factoryBean.setDataSource(getDataSource());

         Properties props = new Properties();
         props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
         props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

         factoryBean.setHibernateProperties(props);
         factoryBean.setAnnotatedClasses(User.class, Car.class);
         logger.info("SessionFactory initialized successfully");
      } catch (Exception e) {
         logger.error("Error initializing SessionFactory", e);
      }
      return factoryBean;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      logger.info("Initializing TransactionManager");
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      try {
         transactionManager.setSessionFactory(getSessionFactory().getObject());
         logger.info("TransactionManager initialized successfully");
      } catch (Exception e) {
         logger.error("Error initializing TransactionManager", e);
      }
      return transactionManager;
   }
}