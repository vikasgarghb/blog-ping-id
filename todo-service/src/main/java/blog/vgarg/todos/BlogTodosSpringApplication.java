package blog.vgarg.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogTodosSpringApplication {

	public static void main(String[] args) {
//		setUpTrustStoreForApplication();
		SpringApplication.run(BlogTodosSpringApplication.class, args);
	}

//	private static void setUpTrustStoreForApplication() {
//		System.setProperty("javax.net.ssl.trustStore", "/Library/Java/JavaVirtualMachines/adoptopenjdk-14.jdk/Contents/Home/conf/security/cacerts");
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//	}
}
