package com.example.demo;





import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import login.LoginAdmin;
import login.LoginUser;

@SpringBootApplication
@Configuration // java controller
@ComponentScan(basePackages = "controller, service, login")
// WebMvcConfigurer : MVC 태그 사용시 implements 해야한다
// web.xml : encoding- utf-8, sitemesh,dispatcher 
// springmvc-servlet.xml, web.xml, sitemesh.xml 사용안함


public class Kicspringmvc2308Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Kicspringmvc2308Application.class, args);
	}

	@Bean // mybatis spring boot 가 만든 mybatis db pool을 사용 한다.
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);		
		
		sessionFactory.setConfigLocation(
				new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml")
				);
		return sessionFactory.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean
	public FilterRegistrationBean<ConfigurableSiteMeshFilter> siteMethFilter(){
		FilterRegistrationBean<ConfigurableSiteMeshFilter> filter = 
				new FilterRegistrationBean<ConfigurableSiteMeshFilter>();
		filter.setFilter(new ConfigurableSiteMeshFilter() {
			protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
				builder
						.addDecoratorPath("/member/*", "/layout/layout.jsp")
						.addDecoratorPath("/board/*", "/layout/layout.jsp")
						.addExcludedPath("board/boardCommentPro")
						.addExcludedPath("member/pictureimgForm")
						.addExcludedPath("member/picturePro");
						
			}});
		return filter;	
	}
	
	@Autowired
	LoginUser loginInterceptor;
	
	@Autowired
	LoginAdmin adminInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("interceptors");
		registry
				.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/*")
				.addPathPatterns("/member/memberList");
		
		registry
				.addInterceptor(loginInterceptor)
				.addPathPatterns("/member/memberInfo")
				.addPathPatterns("/member/memberUpdateForm")
				.addPathPatterns("/member/memberDelete");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("view/board/images/*")  // browser url
				.addResourceLocations("/WEB-INF/view/board/images/"); // 실행 위치
		
	}
	
	
	
	
}
