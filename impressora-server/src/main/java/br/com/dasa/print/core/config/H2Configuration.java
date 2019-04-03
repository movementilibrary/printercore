package br.com.dasa.print.core.config;

/*@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2EntityManager",
        transactionManagerRef = "h2TransactionManager",
        basePackages = { "br.com.dasa.print.core.redis.repository"}
)*/
public class H2Configuration {
   /* @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean h2EntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(h2DataSource());
        em.setPackagesToScan(new String[] { "br.com.dasa.print.core.redis.model" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource h2DataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("spring.datasource.redis.driver-class-name")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("spring.datasource.redis.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("spring.datasource.redis.username")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("spring.datasource.redis.password")));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager h2TransactionManager() {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(h2EntityManager().getObject());
        return transactionManager;
    }
    */
}

