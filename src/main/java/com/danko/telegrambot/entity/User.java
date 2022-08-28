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
@Table(name = "users")
@NoArgsConstructor
@SuperBuilder
@Data
public class User extends BaseEntity {
    @Column(name = "tgid")
    private Long tgId;

    @Column(name = "is_admin")
    protected Boolean isAdmin;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "language_level_id", referencedColumnName = "id")
    private LanguageLevel languageLevel;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "topics_id", referencedColumnName = "id")
    private Topic topic;
}
