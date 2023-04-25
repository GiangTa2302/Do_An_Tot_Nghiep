package doan.com.vn.service.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doan.com.vn.entity.GiaoVien;
import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.User;
import doan.com.vn.repository.GiaoVienRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private HocSinhRepository hocSinhRepository;

    @Override
    public UserDetails loadUserByUsername(String account)
            throws UsernameNotFoundException {

        User userLogged = userRepository.findByUsername(account);
        if (userLogged == null) {
            throw new UsernameNotFoundException("User not found " + account);
        }

        Optional<HocSinh> hsOptional = hocSinhRepository
                .findById(userLogged.getUsername());

        Optional<GiaoVien> gvOptional = giaoVienRepository
                .findById(userLogged.getUsername());

        if (hsOptional.isPresent()) {
            userLogged.setHodem(hsOptional.get().getHodem());
            userLogged.setTen(hsOptional.get().getTen());
        } else if (gvOptional.isPresent()) {
            userLogged.setHodem(gvOptional.get().getHodem());
            userLogged.setTen(gvOptional.get().getTen());
        }
        CustomUserDetail userDetail = new CustomUserDetail(userLogged);
        return userDetail;
    }

}