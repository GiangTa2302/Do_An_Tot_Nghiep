package doan.com.vn.dto;

import java.util.ArrayList;
import java.util.List;

import doan.com.vn.model.CauHoiModel;

public class CauHoiDTO {
    private List<CauHoiModel> chModels = new ArrayList<CauHoiModel>();

    public void addCauHoi(CauHoiModel chModel) {
        this.chModels.add(chModel);
    }

    public List<CauHoiModel> getChModels() {
        return chModels;
    }

    public void setChModels(List<CauHoiModel> chModels) {
        this.chModels = chModels;
    }

}
