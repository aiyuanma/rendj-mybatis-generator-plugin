package cn.net.aiyuanma.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @project: springcloudtest
 * @description:
 * @author: RenDongJi
 * @date: 2018/7/12 15:49.
 * @version: v1.0
 */
public class ExampleCommentPlugin extends PluginAdapter {

    private String author = "";
    private String project = "";

    @Override
    public boolean validate(List<String> list) {
        //作者
        this.author = this.properties.getProperty("author");
        if (null == this.author){
            this.author = "";
        }
        //项目名称
        this.project = this.properties.getProperty("project");
        if (null == this.project){
            this.project = "";
        }
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @project: " + this.project);
        topLevelClass.addJavaDocLine(" * @description: " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * @author: " + this.author);
        topLevelClass.addJavaDocLine(" * @date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        topLevelClass.addJavaDocLine(" * @version: v1.0");
        topLevelClass.addJavaDocLine(" */");
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }
}
