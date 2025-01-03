package io.aditya.kam.convertor;

import io.aditya.kam.entity.CommonEntity;
import io.aditya.kam.model.CommonModel;


public class cov extends Convertor<CommonModel, CommonEntity>{
  @Override
  public <T extends CommonModel> CommonEntity covertToModel(CommonEntity entity) {
    return null;
  }

//  public <T extends CommonModel> toEntity();
}
