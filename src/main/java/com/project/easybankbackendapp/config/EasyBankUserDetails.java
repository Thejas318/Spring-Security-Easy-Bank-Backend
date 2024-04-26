package com.project.easybankbackendapp.config;

import com.project.easybankbackendapp.model.Authority;
import com.project.easybankbackendapp.model.Customer;
import com.project.easybankbackendapp.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class EasyBankUserDetails implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("I am being used for authentication");
        String userName;
        String password;
        List<GrantedAuthority> authorities;

        List<Customer> customer = customerRepository.findByEmail(username);

        if(customer.size() == 0 ){
            throw new UsernameNotFoundException("User details not found for the user: "  + username);
        }
        else{
            userName = customer.get(0).getEmail();
            password = customer.get(0).getPwd();
//            authorities = new ArrayList<>();
//            authorities.add( new SimpleGrantedAuthority(customer.get(0).getRole()));
            log.info("The list of authorities in EasybankUserDetails: {}", getGrantedAuthorites(customer.get(0).getAuthorities()));
        }
        return new User(userName, password, getGrantedAuthorites(customer.get(0).getAuthorities()));
    }

    private List<GrantedAuthority> getGrantedAuthorites(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority: authorities){
            grantedAuthorities.add( new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }
}
