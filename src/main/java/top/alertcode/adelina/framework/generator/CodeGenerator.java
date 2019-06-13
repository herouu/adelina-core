package top.alertcode.adelina.framework.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import top.alertcode.adelina.framework.controller.BaseController;
import top.alertcode.adelina.framework.service.impl.BaseService;

import java.util.ArrayList;
import java.util.List;


@Component
/**
 * <p>CodeGenerator class.</p>
 *
 * @author alertcode
 * @version $Id: $Id
 */
public class CodeGenerator {

    @Autowired
    private Environment environment;


    /**
     * 代码生成器
     *
     * @param author            the author 作者名称
     * @param modelName         the model name 模块名称
     * @param tableNames        the table names 表名 多个表'，'分割
     * @param parentPackageName the parent package name 包名
     * @param env               the env  包路径所在环境  dev or test
     */
    public void exec(String author, String modelName, String tableNames, String parentPackageName, String env) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        if ("test".equals(env)) {
            gc.setOutputDir(projectPath + "/src/test/java");
        } else {
            gc.setOutputDir(projectPath + "/src/main/java");
        }
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(environment.getProperty("spring.datasource.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(environment.getProperty("spring.datasource.driver-class-name"));
        dsc.setUsername(environment.getProperty("spring.datasource.username"));
        dsc.setPassword(environment.getProperty("spring.datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName(modelName);
        pc.setParent(parentPackageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("template/entity.java");
        templateConfig.setService("template/service.java");
        templateConfig.setServiceImpl("template/serviceImpl.java");
        templateConfig.setController("template/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass(BaseController.class.getName());
        strategy.setSuperServiceImplClass(BaseService.class.getName());
        strategy.setInclude(tableNames.split(","));
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
