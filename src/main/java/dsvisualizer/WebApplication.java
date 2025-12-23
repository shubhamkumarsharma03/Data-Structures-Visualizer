package dsvisualizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"dsvisualizer"})
@RestController
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @GetMapping("/api/health")
    public HealthResponse health() {
        return new HealthResponse("Data Structures Visualizer API is running");
    }

    @GetMapping("/")
    public String index() {
        return "Data Structures Visualizer Web API. Visit /api/health or check /swagger-ui.html";
    }

    public static class HealthResponse {
        public String message;

        public HealthResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
