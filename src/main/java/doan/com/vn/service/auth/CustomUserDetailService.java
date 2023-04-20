package doan.com.vn.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doan.com.vn.entity.User;
import doan.com.vn.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        
        User userLogged = userRepository.findByUsername(account);
        if(userLogged == null) {
            throw new UsernameNotFoundException("User not found " + account);
        }
        
        CustomUserDetail userDetail =  new CustomUserDetail(userLogged);
        return userDetail;
    }

}