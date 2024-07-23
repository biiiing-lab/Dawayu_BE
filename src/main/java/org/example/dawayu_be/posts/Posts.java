package org.example.dawayu_be.posts;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dawayu_be.users.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "posts")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    private String title;
    private String content;
    private int likesCount;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime postRegisterDate;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private Users userNo;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void likeCount() {
        this.likesCount += 1;
    }

    public void discountLike() {
        this.likesCount -= 1;
    }



}
