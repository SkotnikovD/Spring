package com.skovdev.springlearn.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_generator")
    @SequenceGenerator(name = "posts_id_generator", sequenceName = "posts_post_id_seq", allocationSize = 1)
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "post_text", nullable = false)
    private String text;

    @Column(name = "created_at")
    private Instant createdDate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User author;

}
