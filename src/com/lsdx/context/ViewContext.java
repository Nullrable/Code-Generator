package com.lsdx.context;

import com.lsdx.view.entry.FmeMain;
import com.lsdx.view.setting.DlgDatasourceSetting;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 17:14
 * @Modified By：
 */
public class ViewContext {

    private static final ViewContext INTANCE = new ViewContext();

    public static ViewContext getInstance(){
        return INTANCE;
    }


    private DlgDatasourceSetting fmeSetting;
    private FmeMain fmeMain;

    public FmeMain getFmeMain(){
        if(fmeMain == null){
            fmeMain = new FmeMain();
        }
        return fmeMain;
    }
}
