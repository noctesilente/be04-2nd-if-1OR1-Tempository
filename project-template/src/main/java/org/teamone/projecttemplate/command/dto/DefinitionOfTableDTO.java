package org.teamone.projecttemplate.command.dto;

import lombok.Data;

@Data
public class DefinitionOfTableDTO {
    private int id;
    private int definitionNo;
    private String tableName;
    private String propertyName;
    private boolean primaryKey;
    private boolean foreignKey;
    private boolean nullAble;
    private String columnName;
    private String defaultValue;
    private String dataType;
    private String note;
    private int projectId;
}
