package com.XMLiWS.microservices.feedservice.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.XMLiWS.microservices.feedservice.bean.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

		@Query(value="SELECT * from POST where published < ?1 AND seeable ORDER BY published DESC LIMIT 10", nativeQuery=true)
        ArrayList<Post> findForUnregistered(Date begindate);
}
