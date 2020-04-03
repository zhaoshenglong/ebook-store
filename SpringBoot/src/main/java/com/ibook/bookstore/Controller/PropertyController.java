package com.ibook.bookstore.Controller;

import com.ibook.bookstore.Service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/api/public/views")
    public int incrementViews() {
        return propertyService.increment();
    }
}
