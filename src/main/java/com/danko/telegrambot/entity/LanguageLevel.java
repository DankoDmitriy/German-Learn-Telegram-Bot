package com.danko.telegrambot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "language_level")
@NoArgsConstructor
@SuperBuilder
@Data
public class LanguageLevel extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;
}
