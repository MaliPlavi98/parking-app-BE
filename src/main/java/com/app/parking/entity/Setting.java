package com.app.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "setting")
@Getter
@Setter
@RequiredArgsConstructor
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", unique = true, nullable = false)
    private String keyName;  // e.g. "about_us", "contact_phone", "price_day"

    @Column(name = "value_text", columnDefinition = "TEXT")
    private String value;    // can store text, numbers, JSON, HTML

}
