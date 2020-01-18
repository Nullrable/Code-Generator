package ${ServiceImplPackage};

import java.io.Serializable;

import java.util.List;

import javax.annotation.Autowired;

import org.springframework.stereotype.Service;


/**
*
* describe: ${(table.comment)!}
* author: ${author}
* date: ${date}
*/
@Service
public interface ${table.entityName}ServiceImpl {


    @Autowired
    private ${table.entityName}ServiceDao;

    ${table.entityName} save(${table.entityName} model){

        model = ${table.entityName}Dao.save(model);

        return model;

    }

    <#assign pkVar = ""/>
    <#list table.pkFields as field>
        <#if field_index == 0>
            <#assign pkVar = "${field.propertyType} ${field.propertyName}"/>
        <#else>
            <#assign pkVar =  "${pkVar}, " + "${field.propertyType} ${field.propertyName}"  />
        </#if>
    </#list>

    void delete(${pkVar}){
        ${table.entityName}Dao.delete(${pkVar});
    }


    ${table.entityName} update(${table.entityName} model){

        model = ${table.entityName}Dao.update(model);

        return model;
    }

    ${table.entityName} read(${pkVar}){

        ${table.entityName} model = ${table.entityName}Dao.read(model);

        return model;

    }

    List<${table.entityName}> list(${table.entityName}Query query){

        List<${table.entityName}> list = ${table.entityName}Dao.list(query);

        return list;
    }
}
