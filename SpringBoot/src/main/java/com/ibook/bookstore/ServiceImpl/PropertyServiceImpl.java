package com.ibook.bookstore.ServiceImpl;

import com.ibook.bookstore.Service.PropertyService;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {
    private int views;

    @Override
    public synchronized int increment() {
        views++;
        return views;
    }
}
