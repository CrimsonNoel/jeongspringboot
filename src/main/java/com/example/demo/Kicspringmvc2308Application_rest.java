package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// static / index.html
@SpringBootApplication
@RestController
// @CrossOrigin(origins = "http://ip:port") //ex : "http://192.168.0.12:8080"
public class Kicspringmvc2308Application_rest {

	public static void main(String[] args) {
		SpringApplication.run(Kicspringmvc2308Application_rest.class, args);
	}
	
	@RequestMapping("/index")
	public List<Member> index(){
		
		Member m1 = new Member();
		m1.setName("케아이씨");
		m1.setAge(20);
		Member m2 = new Member();
		m2.setName("케아이씨");
		m2.setAge(20);
		
		List<Member> li = new ArrayList<>();
		li.add(m1);
		li.add(m2);
		return li;
		
	}

}
