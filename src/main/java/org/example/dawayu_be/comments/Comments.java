package org.example.dawayu_be.comments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dawayu_be.articles.Articles;
import org.example.dawayu_be.users.Users;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Table(name = "comments")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    private String content;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime commentRegisterDate;

    @ManyToOne
    @JoinColumn(name = "userNo")
    private Users userNo;

    @ManyToOne
    @JoinColumn(name = "articleNo")
    private Articles articleNo;

    public void update(String content) {
        this.content = content;
    }

}
