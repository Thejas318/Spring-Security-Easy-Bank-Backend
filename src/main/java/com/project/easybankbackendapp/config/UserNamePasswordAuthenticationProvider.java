package com.project.easybankbackendapp.config;

import com.project.easybankbackendapp.model.Customer;
import com.project.easybankbackendapp.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider {

    CustomerRepository customerRepository;

    PasswordEncoder passwordEncoder;

    EasyBankUserDetails easyBankUserDetails;

    public UserNamePasswordAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, EasyBankUserDetails easyBankUserDetails) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.easyBankUserDetails = easyBankUserDetails;
    }

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Cutomized authentication provider being used by provider manager");
        String userName = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
//        List<Customer> customer = new ArrayList<>();
        UserDetails userDetails;
        try{
            userDetails = this.easyBankUserDetails.loadUserByUsername(userName);
//            customer = this.customerRepository.findByEmail(userName);
        }
        catch (Exception ex){
            log.info("Customer not found - {}", ex.getMessage());
            throw new BadCredentialsException("No user registered with this details");
        }
        if(passwordEncoder.matches(rawPassword, userDetails.getPassword())){
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(userDetails.getAuthorities().toString()));
            log.info("The list of authorities in UserNamePassowrdAuthenticationProvider: {}", userDetails.getAuthorities());
            return new UsernamePasswordAuthenticationToken(userName, rawPassword, userDetails.getAuthorities());
        }
        else{
            throw new BadCredentialsException("Invalid Credentials!");
        }
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
