package doan.com.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.Notify;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long>{
    
}
