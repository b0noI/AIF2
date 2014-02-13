package com.aif.language.model;

import com.aif.associations.model.IItem;

public interface IBaseWord extends IItem {

    public IPostfix[] getPostfixes();

    public IPrefix[] getPrefixes();

}
