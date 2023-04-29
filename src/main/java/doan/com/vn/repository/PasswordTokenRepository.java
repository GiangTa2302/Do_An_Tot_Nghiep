package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.PasswordResetToken;
import doan.com.vn.entity.User;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUser(User user);
}
