define(function(){
  var module = {};

  function loadModule(domElement) {
    var element = $(domElement),
        moduleName = element.data("module");

    require([moduleName], function(module) {
      module.init(element);
    });
  };

  module.execute = function(element) {
    var element = element || $("html"),
        dataModules = $("[data-module]", element);
    _.each(dataModules, loadModule);
  };

  return module;
});