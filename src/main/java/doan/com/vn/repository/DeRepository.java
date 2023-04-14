package doan.com.vn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.De;

@Repository
public interface DeRepository extends JpaRepository<De, String>{
    @Query(value = "select * from de d where d.ma_mon = ?1 and loai_de = 1", nativeQuery = true)
    Page<De> findByLoaiDeTrue(String maMon, Pageable pageable);
    
    @Query(value = "select * from de d where d.ma_mon = ?1 and loai_de = 0", nativeQuery = true)
    Page<De> findByLoaiDeFalse(String maMon, Pageable pageable);
}
