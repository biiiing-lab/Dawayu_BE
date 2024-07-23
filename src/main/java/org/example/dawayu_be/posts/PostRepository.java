package org.example.dawayu_be.posts;

import org.example.dawayu_be.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByOrderByPostRegisterDateDesc();
    List<Posts> findByUserNo(Users user);
}
