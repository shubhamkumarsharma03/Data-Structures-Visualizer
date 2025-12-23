package dsvisualizer.web.controller;

import org.springframework.web.bind.annotation.*;
import dsvisualizer.web.model.WebArray;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/array")
@CrossOrigin(origins = "*")
public class WebArrayController {
    
    private static WebArray array = new WebArray();

    @PostMapping("/insert")
    public Map<String, Object> insert(@RequestParam int value) {
        array.insert(value);
        return createResponse("INSERT", true, "Inserted " + value);
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestParam int index) {
        Integer deleted = array.delete(index);
        if (deleted == null) {
            return createResponse("DELETE", false, "Invalid index");
        }
        return createResponse("DELETE", true, "Deleted " + deleted);
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam int value) {
        int index = array.search(value);
        boolean found = index >= 0;
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "SEARCH");
        response.put("success", found);
        response.put("message", found ? "Found at index " + index : "Not found");
        response.put("elements", array.getElements());
        response.put("size", array.size());
        return response;
    }

    @GetMapping("/get")
    public Map<String, Object> getArray() {
        Map<String, Object> response = new HashMap<>();
        response.put("elements", array.getElements());
        response.put("size", array.size());
        response.put("capacity", array.getCapacity());
        return response;
    }

    @PostMapping("/reset")
    public Map<String, Object> reset() {
        array.reset();
        return createResponse("RESET", true, "Array reset");
    }

    private Map<String, Object> createResponse(String operation, boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", operation);
        response.put("success", success);
        response.put("message", message);
        response.put("elements", array.getElements());
        response.put("size", array.size());
        return response;
    }
}
