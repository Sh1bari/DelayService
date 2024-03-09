package com.example.delayservice.models;

import lombok.*;

/**
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private Integer id;
    private String title;
    private String owner;
    private String sentTime;
    private String createdAt;
    private String payload;
}
