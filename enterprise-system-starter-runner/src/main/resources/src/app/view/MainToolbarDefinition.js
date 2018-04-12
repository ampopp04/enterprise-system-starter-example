/**
 * The <class>System.view.MainToolbarDefinition</class> defines
 *  the main toolbar menu that is hooked in from the migrations.
 *
 * @author Andrew
 */
Ext.define('System.view.MainToolbarDefinition', {
    extend: 'Ext.container.Container',

    ///////////////////////////////////////////////////////////////////////
    ////////                                                       Properties                                                      //////////
    //////////////////////////////////////////////////////////////////////

    xtype: 'main-toolbar-definition',

    ///////////////////////////////////////////////////////////////////////
    ////////                                                          Methods                                                       //////////
    //////////////////////////////////////////////////////////////////////

    listeners: {

        added: function (container, index, eOpts) {
            //Add the main toolbar button to open our basic starter window
            container.up().insert(0, [
                {xtype: 'tbspacer'},
                {
                    text: 'Basic Starter',
                    iconCls: 'x-fa fa-user-plus',
                    menu: new Ext.menu.Menu({
                        items: [{
                            text: 'Starter Window',
                            iconCls: 'x-fa fa-gears',
                            handler: function () {
                                var win = Ext.create('System.view.StarterWindow', {});
                                win.show();
                            }
                        }]
                    })
                }
            ]);

        }

    }

});