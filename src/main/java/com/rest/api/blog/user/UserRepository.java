package com.rest.api.blog.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@EntityGraph(attributePaths = {"comments", "articles", "roles"})
	User findByUsername(String username);
}
