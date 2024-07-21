package org.example.dawayu_be.articles;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dawayu_be.users.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "articles")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleNo;

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

}
