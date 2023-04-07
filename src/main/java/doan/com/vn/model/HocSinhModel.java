package doan.com.vn.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HocSinhModel {
    @NotEmpty(message = "Họ đệm không được để trống.")
    private String hodem;
    
    @NotEmpty(message = "Tên không được để trống.")
    private String ten;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Ngày sinh không được để trống.")
    private Date ngaySinh;
    
    private boolean gioiTinh;
    
    @NotEmpty(message = "Tỉnh không được để trống.")
    private String tinh;
    
    @NotEmpty(message = "Quận không được để trống.")
    private String quan;
    
    @NotEmpty(message = "Phường không được để trống.")
    private String phuong;
    
    private String anhHS;
    
    private String ghiChu;
    
    private String tenLop;
    
    @NotEmpty(message = "Tôn giáo không được để trống.")
    private String tonGiao;
    
    @NotEmpty(message = "Tên phụ huynh không được để trống.")
    private String tenPH;
    
    @Positive(message = "Năm sinh không được để trống.")
    private int namSinh;
    
    @NotEmpty(message = "Nghề nghiệp không được để trống.")
    private String ngheNghiep;
    
    @NotEmpty(message = "Quan hệ không được để trống.")
    private String loaiQH;
    
    @NotEmpty(message = "Điện thoại phụ huynh không được để trống.")
    private String dienThoai;
    
    private int maDanToc;
    
    @NotEmpty(message = "Mã thẻ BHYT không được để trống.")
    private String maThe;
    
    private int maHL;
    
    public HocSinhModel(String tenLop) {
        this.tenLop = tenLop;
    }
}
