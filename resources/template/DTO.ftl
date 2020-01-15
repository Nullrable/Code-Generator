package ${DTOPackage};

import java.io.Serializable;

/**
*
* 描述: ${(table.comment)!}
* author: ${author}
* date: ${date}
*/
public class ${table.entityName}DTO implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- 循环属性名称 -->
<#list table.entityFields as field>
    <#if field.comment?? && field.comment != ''>
    /**
    * ${field.comment}
    */
    </#if>
    private ${field.propertyType} ${field.propertyName};

</#list>
<#-- 循环set/get方法 -->
<#list table.entityFields as field>
    <#if field.propertyType == "Boolean">
        <#assign getprefix="is"/>
    <#else>
        <#assign getprefix="get"/>
    </#if>
    public ${field.propertyType} ${getprefix}${field.propertyName?cap_first}() {
        return ${field.propertyName};
    }

    public void set${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName}) {
        this.${field.propertyName} = ${field.propertyName};
    }
</#list>
}
