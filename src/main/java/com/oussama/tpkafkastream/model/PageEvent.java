package com.oussama.tpkafkastream.model;

import lombok.*;

import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class PageEvent {
    private String name;
    private String user;
    private Date date;
    private long duration;
}
