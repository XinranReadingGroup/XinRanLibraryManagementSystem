package com.xinran.constant;

/**
 * Created by 高海军 帝奇 74394 on 2016 December  20:02.
 */
public interface SystemConfig {

    //     TUNE 保存文件路径 profile,在测试环境下支持选择在临时目录选择

    String commonPrefix = "/home/admin/xinran/";

//    String commonPrefix = "/Users/ghj/xinran/";

    String UGC_IMG_DIR =commonPrefix+ "upload/img/";

    String TEMP_IMG_DIR =commonPrefix+ "temp/";

}
