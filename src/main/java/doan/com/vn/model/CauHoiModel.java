package doan.com.vn.model;

import java.util.ArrayList;
import java.util.List;

import doan.com.vn.entity.DapAn;
import lombok.Data;

@Data
public class CauHoiModel {
    private String deBai;
    private String anh;
    private String dapAnDung;
    private String maDe;
    
    private List<DapAn> dapAns = new ArrayList<DapAn>();
    private String daA;
    private String daB;
    private String daC;
    private String daD;

    public CauHoiModel() {
        super();
    }

    public CauHoiModel(String maDe) {
        super();
        this.maDe = maDe;
    }

    public List<DapAn> getDapAns() {
        this.dapAns.add(new DapAn("A"));
        this.dapAns.add(new DapAn("B"));
        this.dapAns.add(new DapAn("C"));
        this.dapAns.add(new DapAn("D"));
        return dapAns;
    }

    public void setDapAns(List<DapAn> dapAns) {
        this.dapAns = dapAns;
    }
}
