package doan.com.vn.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GiaoVienModel {
    @NotEmpty(message = "First name cannot be empty.")
    private String hodem;
    
    @NotEmpty(message = "Last name cannot be empty.")
    private String ten;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth cannot be empty.")
    private Date ngaySinh;
    
    @NotNull(message = "Gender cannot be empty.")
    private boolean gioiTinh;
    
    @NotEmpty(message = "Province cannot be empty.")
    private String tinh;
    
    @NotEmpty(message = "District cannot be empty.")
    private String quan;
    
    @NotEmpty(message = "Ward cannot be empty.")
    private String phuong;
    
//    @NotEmpty(message = "Imagine cannot be empty.")
    private String anhGV;
    
    @NotEmpty(message = "Education cannot be empty.")
    private String hocVan;
    
    @NotEmpty(message = "Phone number cannot be empty.")
    private String dienThoai;
    
    @NotEmpty(message = "Nation cannot be empty.")
    private String tonGiao;
    
    private Integer maDanToc;
    
    private String maMon;
    
    private List<String> lops;
}
