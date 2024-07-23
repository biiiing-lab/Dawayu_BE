package org.example.dawayu_be.likes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dawayu_be.posts.Posts;
import org.example.dawayu_be.users.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "likes")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesNo;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime commentRegisterDate;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private Users userNo;

    @ManyToOne
    @JoinColumn(name = "postNo")
    private Posts postNo;
}
