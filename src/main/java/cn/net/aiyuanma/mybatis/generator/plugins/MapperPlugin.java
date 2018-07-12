package cn.net.aiyuanma.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
/**
 * @project: springcloud-test
 * @description: 添加@Mapper注解插件
 * @use: <plugin type="com.ren.test.springcloud.plugins.MapperPlugin"><property name="hasMapperAnnotation" value="true"/></plugin>
 * @author: RenDongJi
 * @date: 2018/7/10 10:35.
 * @version: v1.0
 */
public class MapperPlugin extends PluginAdapter {

    private boolean hasMapperAnnotation = true;
    private String author = "";
    private String project = "";


    public MapperPlugin() {
    }

    public boolean validate(List<String> warnings) {
        try {
            String hma = this.properties.getProperty("hasMapperAnnotation");
            if (null != hma && "TRUE".equals(hma.toUpperCase())) {
                this.hasMapperAnnotation = true;
            } else {
                this.hasMapperAnnotation = false;
            }
        }catch (Exception e){
            this.hasMapperAnnotation = false;
        }

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

    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * @project: " + this.project);
        interfaze.addJavaDocLine(" * @description: " + introspectedTable.getFullyQualifiedTable());
        interfaze.addJavaDocLine(" * @author: " + this.author);
        interfaze.addJavaDocLine(" * @date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        interfaze.addJavaDocLine(" * @version: v1.0");
        interfaze.addJavaDocLine(" */");

        //添加@Mapper注解
        if (this.hasMapperAnnotation) {

            FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper");
            interfaze.addImportedType(serviceType);
            interfaze.addAnnotation("@Mapper");

            return true;
        } else {
            return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
        }
    }

}
