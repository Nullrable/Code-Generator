package ${ServicePackage};

import java.io.Serializable;

/**
*
* 描述: ${(table.comment)!}
* author: ${author}
* date: ${date}
*/
public interface ${table.entityName}Service {


    /**
    * 保存
    * @param ${table.entityName}
    * @return
    */
    ${table.entityName} save(${table.entityName} model);

    <#assign pkVar = ""/>
    <#list table.pkFields as field>
        <#if field_index == 0>
            <#assign pkVar = "${field.propertyType} ${field.propertyName}"/>
        <#else>
            <#assign pkVar =  "${pkVar}, " + "${field.propertyType} ${field.propertyName}"  />
        </#if>
    </#list>

    /**
    * 删除
<#list table.pkFields as field>
    * @param ${field.propertyName}
</#list>
    * @return
    */
    void delete(${pkVar});


    /**
    * 更新
    * @param ${table.entityName}
    * @return
    */
    ${table.entityName} update(${table.entityName} model);

    /**
    * 读取
<#list table.pkFields as field>
    * @param ${field.propertyName}
</#list>
    * @return
    */
    ${table.entityName} read(${pkVar});


    /**
    * 查询
    * @param ${table.entityName}Query
    * @return
    */
    List<${table.entityName}> list(${table.entityName}Query query);
}
