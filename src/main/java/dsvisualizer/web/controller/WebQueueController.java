package dsvisualizer.web.controller;

import org.springframework.web.bind.annotation.*;
import dsvisualizer.web.model.WebQueue;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = "*")
public class WebQueueController {
    
    private static WebQueue queue = new WebQueue();

    @PostMapping("/enqueue")
    public Map<String, Object> enqueue(@RequestParam int value) {
        queue.enqueue(value);
        return createResponse("ENQUEUE", true, "Enqueued " + value);
    }

    @PostMapping("/dequeue")
    public Map<String, Object> dequeue() {
        Integer dequeued = queue.dequeue();
        if (dequeued == null) {
            return createResponse("DEQUEUE", false, "Queue is empty");
        }
        return createResponse("DEQUEUE", true, "Dequeued " + dequeued);
    }

    @GetMapping("/peek")
    public Map<String, Object> peek() {
        Integer front = queue.peek();
        if (front == null) {
            return createResponse("PEEK", false, "Queue is empty");
        }
        return createResponse("PEEK", true, "Front: " + front);
    }

    @GetMapping("/get")
    public Map<String, Object> getQueue() {
        Map<String, Object> response = new HashMap<>();
        response.put("elements", queue.getElements());
        response.put("size", queue.size());
        return response;
    }

    @PostMapping("/reset")
    public Map<String, Object> reset() {
        queue.reset();
        return createResponse("RESET", true, "Queue reset");
    }

    private Map<String, Object> createResponse(String operation, boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", operation);
        response.put("success", success);
        response.put("message", message);
        response.put("elements", queue.getElements());
        response.put("size", queue.size());
        return response;
    }
}
