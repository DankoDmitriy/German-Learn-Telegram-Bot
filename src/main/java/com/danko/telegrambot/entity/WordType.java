package com.danko.telegrambot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "word_type")
@NoArgsConstructor
@SuperBuilder
@Data
public class WordType extends BaseEntity {
    @Column(name = "name")
    private String name;
}
