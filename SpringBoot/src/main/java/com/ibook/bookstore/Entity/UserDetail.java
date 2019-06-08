package com.ibook.bookstore.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "user")
@Data
@NoArgsConstructor
public class UserDetail {
    @Id
    private String id;

    private String name;

    private Binary avatar;
}
