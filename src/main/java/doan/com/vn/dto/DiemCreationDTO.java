package doan.com.vn.dto;

import java.util.ArrayList;
import java.util.List;

import doan.com.vn.model.DiemModel;

public class DiemCreationDTO {
    private List<DiemModel> diemModels = new ArrayList<DiemModel>();
    
    public void addDiem(DiemModel diemModel) {
        this.diemModels.add(diemModel);
    }

    public List<DiemModel> getDiemModels() {
        return diemModels;
    }

    public void setDiemModels(List<DiemModel> diemModels) {
        this.diemModels = diemModels;
    }
    
}
