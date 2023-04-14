package doan.com.vn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan.com.vn.entity.BaiViet;

@Repository
public interface BaiVietRepository extends JpaRepository<BaiViet, Integer> {
    Page<BaiViet> findByTrangThaiTrue(Pageable pageable);

    List<BaiViet> findByTrangThaiFalseAndDeletedFalse();

    Optional<BaiViet> findByMaBVAndTrangThaiTrue(Integer maBV);
}
