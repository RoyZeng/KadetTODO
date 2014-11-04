/**
 * Created by AlexSoroka on 11/1/2014.
 */
Ext.define('kadetTODO.view.MainViewport', {

    extend: 'Ext.container.Viewport',
    layout: 'border',

    renderTo: Ext.getBody(),

    requires: [
        'kadetTODO.view.TabPanel',
        'kadetTODO.view.NavigationPanel',
        'kadetTODO.view.common.Header',
        'kadetTODO.view.common.Footer'
    ],

    items: [
        {
            region: 'north',
            xtype: 'commonHeader',
            autoHeight: true,
            border: false
        },
        {
            region: 'west',
            xtype: 'todoNavigationPanel',
            collapsible: true,
            width: 350,
            split: true,
            autoSize: true,
            layout: 'fit'
        },
        {
            region: 'south',
            xtype: 'commonFooter',
            height: 100,
            minHeight: 100,
            split: true,
            collapsible: true,
            collapsed: true
        },
        {
            region: 'center',
            xtype: 'todoTabPanel',
            collapsible: true
        }
    ]

});