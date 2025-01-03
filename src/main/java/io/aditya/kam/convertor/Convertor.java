package io.aditya.kam.convertor;

import io.aditya.kam.entity.CommonEntity;
import io.aditya.kam.model.CommonModel;

//T is model, s is entity
public abstract class Convertor<T,S> {
//  public abstract <T extends CommonEntity> covertToEntity(<T extends CommonModel> commonModel);
  public abstract <T extends CommonModel> S covertToModel(S entity);

//  public abstract <T extends CommonEntity> T covertToEntity(T entity);

}
