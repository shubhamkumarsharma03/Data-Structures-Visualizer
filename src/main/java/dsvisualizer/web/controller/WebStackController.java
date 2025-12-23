package dsvisualizer.web.controller;

import org.springframework.web.bind.annotation.*;
import dsvisualizer.web.model.WebStack;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stack")
@CrossOrigin(origins = "*")
public class WebStackController {
    
    private static WebStack stack = new WebStack();

    @PostMapping("/push")
    public Map<String, Object> push(@RequestParam int value) {
        stack.push(value);
        return createResponse("PUSH", true, "Pushed " + value);
    }

    @PostMapping("/pop")
    public Map<String, Object> pop() {
        Integer popped = stack.pop();
        if (popped == null) {
            return createResponse("POP", false, "Stack is empty");
        }
        return createResponse("POP", true, "Popped " + popped);
    }

    @GetMapping("/peek")
    public Map<String, Object> peek() {
        Integer top = stack.peek();
        if (top == null) {
            return createResponse("PEEK", false, "Stack is empty");
        }
        return createResponse("PEEK", true, "Top: " + top);
    }

    @GetMapping("/get")
    public Map<String, Object> getStack() {
        Map<String, Object> response = new HashMap<>();
        response.put("elements", stack.getElements());
        response.put("size", stack.size());
        return response;
    }

    @PostMapping("/reset")
    public Map<String, Object> reset() {
        stack.reset();
        return createResponse("RESET", true, "Stack reset");
    }

    private Map<String, Object> createResponse(String operation, boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", operation);
        response.put("success", success);
        response.put("message", message);
        response.put("elements", stack.getElements());
        response.put("size", stack.size());
        return response;
    }
}
