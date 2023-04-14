package doan.com.vn.model;

import java.util.List;

import doan.com.vn.entity.DapAn;
import lombok.Data;

@Data
public class DapAnHSModel {
    private Integer maCH;
    private String deBai;
    private String dapAn;
    private String dapAnDung;
    private List<DapAn> dapAns;

    public DapAnHSModel(Integer maCH, String dapAnDung, String deBai, List<DapAn> dapAns) {
        super();
        this.maCH = maCH;
        this.dapAnDung = dapAnDung;
        this.deBai = deBai;
        this.dapAns = dapAns;
    }

    public DapAnHSModel() {
        super();
    }

}
