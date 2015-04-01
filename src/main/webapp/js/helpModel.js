define([], function() {
  var HelpModel = Backbone.Model.extend({
			 url: '/js/help.json',
    clear: function() {}
  });
  return HelpModel;
});