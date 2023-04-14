package doan.com.vn.dto;

import java.util.ArrayList;
import java.util.List;

import doan.com.vn.model.DapAnHSModel;

public class DapAnHSDTO {
    private List<DapAnHSModel> daHSModels = new ArrayList<DapAnHSModel>();

    public void addDA(DapAnHSModel da) {
        this.daHSModels.add(da);
    }

    public List<DapAnHSModel> getDaHSModels() {
        return daHSModels;
    }

    public void setDaHSModels(List<DapAnHSModel> daHSModels) {
        this.daHSModels = daHSModels;
    }

}
