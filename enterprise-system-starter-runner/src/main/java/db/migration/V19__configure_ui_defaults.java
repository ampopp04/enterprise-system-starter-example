package db.migration;


import com.starter.entities.Department;
import com.starter.entities.Employee;
import com.system.db.migration.data.BaseDataMigration;
import com.system.db.schema.table.column.SchemaTableColumn;
import com.system.db.schema.table.column.SchemaTableColumnRepository;
import com.system.ws.entity.upload.util.EntityPropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.system.util.collection.CollectionUtils.*;


/**
 * The <class>V18__configure_ui_defaults</class> is a migration
 * that configures which columns are hidden or shown by default within the UI.
 * <p>
 * It also configures what their display names will be (What their labels will be for each column/field) and
 * the order in which the column/fields are displayed. This is for any UI component that is generally
 * dynamically generated.
 * <p>
 * These can be overridden in JS code if more case specific behavior is desired.
 *
 * @author Andrew
 */
public class V19__configure_ui_defaults extends BaseDataMigration {

    ///////////////////////////////////////////////////////////////////////
    ////////                                                     Properties                                                       //////////
    /////////////////////////////////////////////////////////////////////

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SchemaTableColumnRepository schemaTableColumnRepository;

    ///////////////////////////////////////////////////////////////////////
    ////////                                                  Data Insertion                                                   //////////
    //////////////////////////////////////////////////////////////////////

    @Override
    protected void insertData() {

        updateEntityUi(Department.class,
                update("name", "Name", "{allowBlank:false}"),
                update("description", "Description")
        );

        updateEntityUi(Employee.class,
                update("name", "Name", "{hidden:true}", "{hidden:false}"),
                update("description", "Description", "{hidden:true}"),
                update("firstName", "First Name", "{allowBlank:false}", "{hidden:true}"),
                update("lastName", "Last Name", "{allowBlank:false}", "{hidden:true}"),
                update("jobTitle", "Job Title", "{allowBlank:false}"),
                update("businessPhone", "Business Phone"),
                update("mobilePhone", "Mobile Phone", null, "{hidden:true}"),
                update("emailAddress", "E-Mail Address"),
                update("manager", "Manager", null, "{hidden:true}", "{reference:{parent: 'Employees', unique: true} }"),
                update("department", "Department", "{allowBlank:false}"),
                update("company", "Company", null, "{hidden:true}"),
                update("streetAddress", "Address", null, "{hidden:true}"),
                update("city", "City", null, "{hidden:true}"),
                update("state", "State", null, "{hidden:true}"),
                update("zipCode", "Zip Code", null, "{hidden:true}"),
                update("country", "Country", null, "{hidden:true}"),
                update("enabled", "Active", "{hidden:true}"),
                update("passwordExpired", "Password Expired", "{hidden:true}")
        );

    }

    private void updateEntityUi(Class tableEntityClass, SchemaTableColumn... schemaTableColumns) {
        updateTableUiConfiguration(tableEntityClass, asList(schemaTableColumns));
    }

    private void updateTableUiConfiguration(Class tableEntityClass, List<SchemaTableColumn> schemaTableColumnList) {
        final int[] count = {100};

        preconfigureColumnsAsHidden(tableEntityClass);

        iterate(iterable(schemaTableColumnList), updateColumn -> {
            updateColumn.setDefaultColumnOrder(count[0]++);
            SchemaTableColumn currentColumn = getSchemaTableColumnRepository().findBySchemaTableNameAndName(tableEntityClass.getSimpleName(), updateColumn.getName());

            if (currentColumn == null) {
                logger.error("Updating UI column display configs and current column is null but it should exist. Table Entity Class: [" + tableEntityClass.getSimpleName() + "], Update Column Name: [" + updateColumn.getName() + "]");
            }

            EntityPropertyUtils.copyNonNullProperties(updateColumn, currentColumn);
            getSchemaTableColumnRepository().save(currentColumn);
        });

    }

    private SchemaTableColumn update(String columnName, String displayName) {
        return update(columnName, displayName, null);
    }

    private SchemaTableColumn update(String columnName, String displayName, String uiFieldConfiguration) {
        return update(columnName, displayName, uiFieldConfiguration, null);
    }

    private SchemaTableColumn update(String columnName, String displayName, String uiFieldConfiguration, String uiColumnConfiguration) {
        return update(columnName, displayName, uiFieldConfiguration, uiColumnConfiguration, null);

    }

    private SchemaTableColumn update(String columnName, String displayName, String uiFieldConfiguration, String uiColumnConfiguration, String uiModelFieldConfiguration) {
        SchemaTableColumn updateColumn = new SchemaTableColumn();

        updateColumn.setName(columnName);
        updateColumn.setDisplayName(displayName);

        updateColumn.setUiFieldConfiguration(uiFieldConfiguration);
        updateColumn.setUiColumnConfiguration(uiColumnConfiguration);
        updateColumn.setUiModelFieldConfiguration(uiModelFieldConfiguration);

        if (uiFieldConfiguration != null && uiFieldConfiguration.contains("hidden:true")) {
            updateColumn.setDisplayHidden(true);
        } else {
            updateColumn.setDisplayHidden(false);
        }

        return updateColumn;
    }

    private void preconfigureColumnsAsHidden(Class tableEntityClass) {
        List<SchemaTableColumn> columnList = getSchemaTableColumnRepository().findBySchemaTableName(tableEntityClass.getSimpleName());

        /**
         * By default make all columns hidden so we only show the ones we want to show.
         */
        configureColumnAsHidden(columnList);

        getSchemaTableColumnRepository().saveAll(columnList);
    }

    private void configureColumnAsHidden(List<SchemaTableColumn> columnList) {
        final Integer[] count = {200};

        /**
         * Configure as a hidden column that is really far down the column order
         */
        iterate(columnList, column -> {
            column.setDisplayHidden(true);
            column.setDefaultColumnOrder(count[0]);
            column.setDisplayName(column.getName());
            count[0]++;
        });
    }

    ///////////////////////////////////////////////////////////////////////
    ////////                                             Basic   Getter/Setters                                          //////////
    //////////////////////////////////////////////////////////////////////

    public SchemaTableColumnRepository getSchemaTableColumnRepository() {
        return schemaTableColumnRepository;
    }

    public void setSchemaTableColumnRepository(SchemaTableColumnRepository schemaTableColumnRepository) {
        this.schemaTableColumnRepository = schemaTableColumnRepository;
    }

}