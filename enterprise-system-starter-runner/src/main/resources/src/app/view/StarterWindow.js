/**
 * The <class>System.view.StarterWindow</class> defines
 * a basic starter window. This demonstrates how the UI can be dynamically generated
 * based on DTO names.
 *
 * @author Andrew
 */
Ext.define('System.view.StarterWindow', {
    extend: 'System.view.component.window.tab.grid.TabGridSystemWindow',

    title: 'Starter Window',

    constructor: function (config) {
        var me = config;

        me.tabs = [
            {
                title: 'Departments',
                modelName: 'Departments'
            }, {
                title: 'Employees',
                modelName: 'Employees'
            }
        ];

        this.callParent([config]);
    }

});