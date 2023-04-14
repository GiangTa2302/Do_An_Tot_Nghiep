package doan.com.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.CauHoi;

@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Integer>{
    @Query(value = "select ch.* from cau_hoi ch where ch.ma_de = ?1", nativeQuery = true)
    List<CauHoi> findByDe(String maDe);
}
