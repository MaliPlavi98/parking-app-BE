package com.app.parking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", unique = true, nullable = false)
    private String keyName;  // e.g. "about_us", "contact_phone", "price_day"

    @Column(name = "value_text", columnDefinition = "TEXT")
    private String value;    // can store text, numbers, JSON, HTML

    public Setting() {}

    public Setting(String keyName, String value) {
        this.keyName = keyName;
        this.value = value;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
