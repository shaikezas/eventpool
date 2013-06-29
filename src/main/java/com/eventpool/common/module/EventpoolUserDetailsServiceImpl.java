package com.eventpool.common.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eventpool.common.entities.User;
import com.eventpool.common.repositories.UserRepository;

public class EventpoolUserDetailsServiceImpl implements EventpoolUserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String loginName)
			throws UsernameNotFoundException {
		System.out.println("Trying to login with login name"+loginName);
		 User user = null;
		try{
		user = userRepository.findByUserName(loginName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test");
        if (user==null) throw new UsernameNotFoundException("Username not found: "+loginName);
        
		UserDetails userDetails = new EventpoolUserDetails(user);
		return userDetails;
	}
	
	
	public User register(User user) {
		/*String email	= boutiqueNew.getEmail();
		String name		= boutiqueNew.getStoreName();
		String password	= boutiqueNew.getPassword();
		
		if (email==null || email.isEmpty()) throw new RuntimeException("No Email provided");
		if (name==null || name.isEmpty()) throw new RuntimeException("No name provided.");
        if (password==null || password.isEmpty()) throw new RuntimeException("No password provided.");
        
		Boutique newUser = null;
		if(ResultStatus.SUCCESS == boutiqueService.ValidateEmail(boutiqueNew.getEmail())) {
        	boutiqueService.AddNewBoutique(conversionService.convert(boutiqueNew, Boutique.class));
		} else {
			throw new RuntimeException("Boutique already registered with : "+email);
		} */
        
        //setUserInSession(newUser);
        return null;
    }
	
	public User getUserFromSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(null == authentication) return null;
        Object principal = authentication.getPrincipal();
        if(null == principal) {
        	return null;
        }
        
        if (principal instanceof EventpoolUserDetails) {
        	EventpoolUserDetails userDetails = (EventpoolUserDetails) principal;
            return userDetails.getUser();
        }
        return null;
    }
	

    public void setUserInSession(User user) {
        SecurityContext context = SecurityContextHolder.getContext();
        EventpoolUserDetails userDetails = new EventpoolUserDetails(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),userDetails.getAuthorities());
        context.setAuthentication(authentication);
        System.out.println("User has been set");
    }
}
