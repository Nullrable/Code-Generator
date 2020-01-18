package com.lsdx.context;

import com.lsdx.service.*;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-01-09 15:10
 * @Modified By：
 */
public class AppContext {

    public static final CodeGenerateService CODE_GENERATE_SERVICE = new CodeGenerateService();

    public static final DatasourceConfigService DATASOURCE_CONFIG_SERVICE = new DatasourceConfigService();

    public static final DatasourceService DATASOURCE_SERVICE = new DatasourceService();

    public static final FileService FILE_SERVICE = new FileService();

    public static final OutputPathService OUTPUT_PATH_SERVICE = new OutputPathService();

    public static final TemplateService TEMPLATE_SERVICE = new TemplateService();

    public static final PackageConfigService PACKAGE_CONFIG_SERVICE = new PackageConfigService();

    public static final AuthorService AUTHOR_SERVICE = new AuthorService();
}
