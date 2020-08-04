package com.cos.securityex01.test;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@RestController
public class OptionalControllerTest {
    
	@Autowired
	private UserRepository userRepository; 
	
	@GetMapping("/test/user/{id}")
	public User 옵셔널_유저찾기(@PathVariable int id) {
//	  Optional<User> userOptional = userRepository.findById(id);
//	  User user;
//	  if(userOptional.isPresent()) {
//		  user = userOptional.get();
//	  }else {
//		  user = new User();
//	  }
//	  
//	  User user = userRepository.findById(id).orElseGet(()-> {
//             return new User();
//		   
		  
//		@Override
//		public User get() {
//		
//			return new User();
//		}
//	});
	 
		User user = userRepository.findById(id).orElseThrow(()->{
	
				return new NullPointerException("값없어");
					   
		});
		
	  return user;
	}
	
}
