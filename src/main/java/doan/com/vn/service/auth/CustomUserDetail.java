package doan.com.vn.service.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import doan.com.vn.entity.Role;
import doan.com.vn.entity.User;

public class CustomUserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;
    
    public CustomUserDetail(){
    }
    
    public CustomUserDetail(User user){
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));
        }
        
        return authorities;
    }
    
    public boolean hasRole(String roleName) {
        return this.user.hasRole(roleName);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
