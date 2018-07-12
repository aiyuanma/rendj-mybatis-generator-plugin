package cn.net.aiyuanma.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @project: springcloud-test
 * @description: 添加Lombok工具注解插件
 * @use: <plugin type="com.ren.test.springcloud.plugins.LombokPlugin"><property name="hasLombok" value="true"/></plugin>
 * @author: RenDongJi
 * @date: 2018/7/11 23:45.
 * @version: v1.0
 */
public class LombokPlugin extends PluginAdapter {

    private boolean hasLombok = true;
    private String author = "";
    private String project = "";

    @Override
    public boolean validate(List<String> list) {
        try {
            //是否添加Lombok支持
            String hl = this.properties.getProperty("hasLombok");
            if (null != hl && "TRUE".equals(hl.toUpperCase())) {
                this.hasLombok = true;
            } else {
                this.hasLombok = false;
            }
        }catch (Exception e){
            this.hasLombok = false;
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

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @project: " + this.project);
        topLevelClass.addJavaDocLine(" * @description: " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * @author: " + this.author);
        topLevelClass.addJavaDocLine(" * @date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        topLevelClass.addJavaDocLine(" * @version: v1.0");
        topLevelClass.addJavaDocLine(" */");

        if (hasLombok) {
            //清除get set toString方法
            topLevelClass.getMethods().clear();

            topLevelClass.addImportedType("lombok.experimental.Accessors");
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");

            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
            topLevelClass.addAnnotation("@Accessors(chain=true)");
            return true;
        } else {
            return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
        }

    }

}
