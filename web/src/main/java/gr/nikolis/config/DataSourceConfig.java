package gr.nikolis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfig {

    private static final String MYSQL_LOCAL = "mysql";
    private static final String MYSQL_CONTAINER = "mysql-container";

    @Value("${spring.datasource.username:root}")
    private String datasourceUserName;
    @Value("${spring.datasource.password:123}")
    private String dataSourcePassword;
    @Value("${spring.datasource.address:localhost}")
    private String datasourceAddress;
    @Value("${spring.datasource.dbname:temp}")
    private String datasourceDBName;

    @Bean
    public String getDatasourceUserName() {
        return datasourceUserName;
    }

    @Bean
    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    @Bean
    public String getDatasourceAddress() {
        return datasourceAddress;
    }

    @Bean
    public String getDatasourceDBName() {
        return datasourceDBName;
    }

    /**
     * On The fly Datasource
     * For testing purposes
     *
     * @return The DataSource object
     */
    @Bean(name = "h2DataSource")
    @Primary
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:file:~/home/public/" + getDatasourceDBName() + "")
                .username(getDatasourceUserName())
                .password(getDataSourcePassword())
                .build();
    }

    /**
     * MySql Datasource
     * <p>
     * todo: allowPublicKeyRetrieval = should be false to prevent malicious attacks , I must find a better approach
     *
     * @return The DataSource object
     */
    @Bean(name = "mySqlDataSource")
    //@Primary
    public DataSource mySqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:" + MYSQL_LOCAL + "://" + getDatasourceAddress() + "/" + getDatasourceDBName() + "?" +
                        "serverTimezone=UTC&" +
                        "useUnicode=true&" +
                        "characterEncoding=utf8&" +
                        "allowPublicKeyRetrieval=true&" +
                        "useSSL=false")
                .username(getDatasourceUserName())
                .password(getDataSourcePassword())
                .build();
    }
}
