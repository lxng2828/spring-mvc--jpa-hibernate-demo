package com.example.app.config;

// Cấu hình tầng Persistence: DataSource, EntityManagerFactory, TransactionManager.

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement // Bật tính năng quản lý giao dịch bằng @Transactional
@EnableJpaRepositories("com.example.app.repository") // Scan các repository
@ComponentScan("com.example.app.service") // Scan các service
public class PersistenceJPAConfig {

    // === Cấu hình DataSource để kết nối tới MySQL ===
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Thay thế 'phone_db' nếu bạn dùng tên database khác.
        // Các tham số để đảm bảo tương thích với MySQL 8.
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/phone_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC");

        // !!! QUAN TRỌNG: Thay đổi username và password của bạn ở đây !!!
        dataSource.setUsername("root");
        dataSource.setPassword("haha123");

        return dataSource;
    }

    // 2. Cấu hình EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.example.app.model"); // Nơi chứa các Entity

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());

        return em;
    }

    // 3. Cấu hình TransactionManager
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    // === Hibernate Dialect cho MySQL 8 ===
    private Properties additionalProperties() {
        Properties properties = new Properties();
        // 'update': Hibernate sẽ tự động tạo/cập nhật schema CSDL khi khởi động.
        // Rất tiện cho môi trường phát triển.
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }
}
