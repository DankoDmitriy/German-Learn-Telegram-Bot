package com.danko.telegrambot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "words")
@NoArgsConstructor
@SuperBuilder
@Data
public class Word extends BaseEntity {
    @Column(name = "article")
    private String article;

    @Column(name = "name")
    private String name;

    @Column(name = "plural")
    private String plural;

    @Column(name = "plural_rul")
    private String pluralRul;

    @Column(name = "translation")
    private String translation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_type_id", referencedColumnName = "id")
    private WordType wordType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic topic;
}
