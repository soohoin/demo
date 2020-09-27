package com.church.simgokchyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// @Bean
	// public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	// 	SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	// 	sessionFactory.setDataSource(dataSource);
		
	// 	Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/page_02/page_02_SQL.xml");
	// 	sessionFactory.setMapperLocations(res);
		
	// 	return sessionFactory.getObject();
	// }

}
