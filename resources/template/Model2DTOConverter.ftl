package ${Model2DTOConverterPackage};

import ${EntityPackage}.${table.entityName}
import ${DTOPackage}.${table.entityName}DTO
import org.springframework.cglib.beans.BeanCopier;

/**
*
* describe: ${(table.comment)!}
* author: ${author}
* date: ${date}
*/
public class ${table.entityName}Converter {

    private static BeanCopier copierdto = BeanCopier.create(${table.entityName}.class, ${table.entityName}DTO.class, false);
    private static BeanCopier copiermodel = BeanCopier.create(${table.entityName}DTO.class, ${table.entityName}.class, false);

    public static ${table.entityName}DTO createDTO(${table.entityName} model) {
        if (model == null) {
        return null;
        }
        ${table.entityName}DTO dto = new ${table.entityName}DTO();
        copierdto.copy(model, dto, null);

        return dto;
    }

    public static ${table.entityName} createModel(${table.entityName}DTO dto) {
        if (dto == null) {
        return null;
        }
        ${table.entityName} model = new ${table.entityName}();
        copiermodel.copy(dto, model, null);

        return model;
    }

    public static Collection<${table.entityName}> createModels(Collection<${table.entityName}DTO> dtos) {
        if (dtos == null) {
        return null;
        }
        List<${table.entityName}> list = new ArrayList<>();
            Iterator<${table.entityName}DTO> iterator = dtos.iterator();
            while (iterator.hasNext()) {
                ${table.entityName}DTO dto = iterator.next();
                ${table.entityName} model = createModel(dto);
                list.add(model);
            }
            return list;
       }

    public static Collection<${table.entityName}DTO> createDTOs(Collection<${table.entityName}> models) {
        if (models == null) {
        return null;
        }
        List<${table.entityName}DTO> list = new ArrayList<>(); Iterator<${table.entityName}> iterator = models.iterator();
        while (iterator.hasNext()) {
            ${table.entityName} model = iterator.next();
            ${table.entityName}DTO dto = createDTO(model);
            list.add(dto);
        }
        return list;
    }
}
