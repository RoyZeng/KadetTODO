/**
 * Created by AlexSoroka on 11/1/2014.
 */
Ext.define('kadetTODO.controller.ProjectsController', {
    extend: 'Ext.app.Controller',


    stores: ['Projects'],
    models: [],
    views: ['NavigationPanel', 'TabPanel'],
    refs: [
        {
            ref: 'myMainTreePanel',
            selector: 'todoNavigationPanel'
        },
        {
            ref: 'mainContainer',
            selector: 'todoTabPanel'
        }
    ],

    routes: {
        'projects': 'onProjects',
        'myPage': 'onMyPage'
    },

    init: function () {
        this.control({
//            'viewport': {
//                render: this.onPanelRendered
//            },
            'todoNavigationPanel button[text=Expand All]': {
                click: this.expandAll
            },
            'todoNavigationPanel button[text=Collapse All]': {
                click: this.collapseAll
            },
            'todoNavigationPanel': {
                itemClick: this.treeItemClick
            }
        });
    },

    onProjects: function () {
        console.log("onProjects");
    },

    onMyPage: function () {
        console.log('onMyPage');
    },

    onPanelRendered: function () {
        //just a console log to show when the panel si rendered
        console.log('The panel was rendered');
    },

    expandAll: function () {
        //expand all the Tree Nodes
        var myTree = this.getMyMainTreePanel();
        myTree.expandAll();
    },

    collapseAll: function () {
        //expand all the Tree Nodes
        var myTree = this.getMyMainTreePanel();
        myTree.collapseAll();
    },

    treeItemClick: function (view, record) {
        var mainContainer = this.getMainContainer();
        //some node in the tree was clicked
        //you have now access to the node record and the tree view

        var newTabTitle = record.get('text');

        var hasSuchName = false;

        var tab = mainContainer.items.find(function (item) {
            return item.title === newTabTitle;
        });
        if (!tab) {

            if (newTabTitle == 'Projects') {
                this.redirectTo('projects');
            } else {
                this.redirectTo('myPage');
            }

            /*Ext.each(mainContainer.getTabBar().items, function (item) {
             console.log(item);
             if (item.title == newTabTitle) {
             hasSuchName = true;
             }
             });*/

            var newTabPanel = Ext.create('Ext.panel.Panel', {
                xtype: 'gridPanel',
                title: record.get('text'),
                html: '',
                closable: true
            });

            Ext.getBody().mask();

            Ext.Ajax.request({

                url: ('/api/projects/' + newTabTitle),
                method: "GET",
                success: function (response, opts) {
                    newTabPanel.update(response.responseText);
                    mainContainer.add(newTabPanel);
                    mainContainer.setActiveTab(newTabPanel);
                    Ext.getBody().unmask();
                },
                failure: function (request) {
                    Ext.MessageBox.show({title: 'Error', msg: "Can't make a request: " + request, buttons: Ext.MessageBox.OK});
                    Ext.getBody().unmask();
                }

            });

        }

    }
});