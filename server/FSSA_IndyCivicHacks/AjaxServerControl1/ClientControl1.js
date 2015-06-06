/// <reference name="MicrosoftAjax.js"/>


Type.registerNamespace("AjaxServerControl1");

AjaxServerControl1.ClientControl1 = function(element) {
    AjaxServerControl1.ClientControl1.initializeBase(this, [element]);
}

AjaxServerControl1.ClientControl1.prototype = {
    initialize: function() {
        AjaxServerControl1.ClientControl1.callBaseMethod(this, 'initialize');
        
        // Add custom initialization here
    },
    dispose: function() {        
        //Add custom dispose actions here
        AjaxServerControl1.ClientControl1.callBaseMethod(this, 'dispose');
    }
}
AjaxServerControl1.ClientControl1.registerClass('AjaxServerControl1.ClientControl1', Sys.UI.Control);

if (typeof(Sys) !== 'undefined') Sys.Application.notifyScriptLoaded();