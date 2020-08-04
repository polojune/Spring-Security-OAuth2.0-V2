package com.cos.securityex01.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.securityex01.model.User;

//JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface UserRepository extends JpaRepository<User, Integer>{
 
	
	//Jpa Naming 전략 
	//SELECT * FROM user WHERE username = 1 ?
      
     User findByUsername(String username);
     
    // @Query(value = "select * from where username = ?1", nativeQuery = true)
   
     //@Query(value = "select * from where email = ?1", nativeQuery = true)
     
     //SELECT * FROM user WHERE provider = ?1 and providerId =?2
     Optional<User> findByProviderAndProviderId(String provider,String providerId);
}
