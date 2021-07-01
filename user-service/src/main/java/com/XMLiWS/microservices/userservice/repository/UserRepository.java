package com.XMLiWS.microservices.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.XMLiWS.microservices.userservice.bean.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
		User findByUserId(Long userId);

		@Query(value="SELECT * from USER where UPPER(name) LIKE UPPER(?1) OR UPPER(surname) LIKE UPPER(?2) OR  UPPER(name) LIKE UPPER(?2) OR UPPER(surname) LIKE UPPER(?1) ", nativeQuery=true)
		List<User> findByNames(String string, String string2);

		List<User> findByNameLikeIgnoreCaseOrSurnameLikeIgnoreCase(String name, String name2);
		
		@Query(value="SELECT * from USER where ( UPPER(name) LIKE UPPER(?1) OR UPPER(surname) LIKE UPPER(?2) OR  UPPER(name) LIKE UPPER(?2) OR UPPER(surname) LIKE UPPER(?1) ) AND privacy = false", nativeQuery=true)
		List<User> findByNamesPublic(String string, String string2);

		@Query(value="SELECT * from USER where ( UPPER(name) LIKE UPPER(?1) OR UPPER(surname) LIKE UPPER(?1) ) AND privacy = false", nativeQuery=true)
		List<User> findByNamePublic(String string);

		List<User> findAllByUsernameLikeIgnoreCase(String hash);

		List<User> findAllByUsernameLikeIgnoreCaseAndPrivacy(String hash, boolean b);



}
