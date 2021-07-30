package com.XMLiWS.microservices.feedservice.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.XMLiWS.microservices.feedservice.bean.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

		@Query(value="SELECT * from POST where published < ?1 AND seeable AND post_type!='story' ORDER BY published DESC LIMIT 10", nativeQuery=true)
        ArrayList<Post> findForUnregisteredPosts(LocalDateTime localDateTime);
		  
		@Query(value="SELECT * from POST where published < ?1 AND published > ?2 AND seeable AND post_type='story' ORDER BY published DESC LIMIT 5", nativeQuery=true)
		ArrayList<Post> findForUnregisteredStories(LocalDateTime localDateTime, LocalDateTime localDateTime2);
		
		@Query(value="SELECT * from POST where userID IN (?1) AND published < ?2 AND published > ?3 AND post_type='story' ORDER BY published", nativeQuery=true)
		ArrayList<Post> findStoriesForRegistered(List<String> followings,
				LocalDateTime now, LocalDateTime minusHours);
		
		@Query(value="SELECT * from POST where userID IN (?1) AND published < ?2 AND post_type!='story' ORDER BY published", nativeQuery=true)
        ArrayList<Post> findPostsForRegistered(Collection<String> ids, LocalDateTime localDateTime);
        
		
		List<Post> findByuserID(String username);

		List<Post> findAllByLocationLikeIgnoreCase(String string);

		List<Post> findAllByLocationLikeIgnoreCaseAndSeeable(String string, boolean b);

		List<Post> findAllBySeeable(boolean b);
		
		@Query(value="select * from post p join liked l where p.postid=l.postid and l.liked=?1", nativeQuery=true)
		List<Post> findLikedPosts(String username);

		@Query(value="select * from post p join disliked d where p.postid=d.postid and d.disliked=?1", nativeQuery=true)
		List<Post> findDislikedPosts(String username);

		

		
		
		

		
}
