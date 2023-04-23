package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.GiaoVien;

@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, String>{
    Page<GiaoVien> findByDeletedFalse(Pageable pageable);
    
    List<GiaoVien> findByDeletedFalse();
    
//    @Query("SELECT gv FROM GiaoVien gv JOIN gv.user u WHERE u.username = ?1")
//    Optional<GiaoVien> findByUsername(String username);
}
