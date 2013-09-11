package com.eventpool.common.module;

/*import org.netvogue.server.webmvc.rest.invoker.UserRepresentation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class NetvogueUserDetails extends User {

	private static final long serialVersionUID = 6468055483876432771L;

	private long salt;

	private UserRepresentation domainUser;

	public NetvogueUserDetails(final String username, final String password,
			final long salt, final UserRepresentation domainUser,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		this.salt = salt;
		this.domainUser = domainUser;
		System.out.println("User password:" + domainUser.getPassword());
		System.out.println("User salt:" + salt);
	}

	public NetvogueUserDetails(final String username, final String password,
			final long salt,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		this.salt = salt;
		System.out.println("User salt1:" + salt);
	}

	public NetvogueUserDetails(final String username, final String password,
			final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired,
			final boolean accountNonLocked, final long salt,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		this.salt = salt;
		System.out.println("User salt2:" + salt);

	}

	public long getSalt() {
		return salt;
	}

	public void setSalt(final long salt) {
		this.salt = salt;
	}

	public UserRepresentation getDomainUser() {
		return domainUser;
	}

	public void setDomainUser(final UserRepresentation domainUser) {
		this.domainUser = domainUser;
	}

}*/

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eventpool.common.entities.User;

public class EventpoolUserDetails implements UserDetails {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final User user;
        
        private static final Logger logger = LoggerFactory
    			.getLogger(EventpoolUserDetails.class);
        
        EventpoolUserDetails(User user) {
                this.user = user;
        }
        
        public Collection<? extends GrantedAuthority> getAuthorities() {
                User.Roles[] roles = user.getRoles();
        if (roles ==null) return Collections.emptyList();
        return Arrays.<GrantedAuthority>asList(roles);
        }

        public String getPassword() {
                logger.debug("Password stored is" + user.getPassword());
                return user.getPassword();
        }

        public String getUsername() {
                return user.getEmail();
        }

        public boolean isAccountNonExpired() {
                return true;
        }

        public boolean isAccountNonLocked() {
                return true;
        }

        public boolean isCredentialsNonExpired() {
                return true;
        }

        public boolean isEnabled() {
                return user.isAccountEnabled();
        }

        public User getUser() {
        return user;
    }
}
