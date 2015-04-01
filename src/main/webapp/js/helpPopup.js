define(['helpModel'], function(HelpModel) {

	var exports = {};
	exports.init = function(item, options) {
		var tmpl = '<div class="help_popupbox" id="help_popupbox">' + 
		'<div class="popup_close"><a></a></div>' + 
		'<div class="help_popupbox_arrow"><%=content%></div>' +
		'</div>';

		var frameTpl = '<div id="sub_popupbox">' +
		'<div class="pacity_box"></div>' + 
		'<div class="sub_popupbox_border">' +
		'<div class="sub_popup_cont">' +
		'<iframe id="help-iframe" frameborder="0"></iframe>' +
		'</div>' +
		'<div class="sub_popup_bot">' +
		'<a class="sub_closeX button_action button paddingtb_7">Close</a>' +
		'<span class="floatR">Go to the <a href="/bp2/carecenter/careCenter.jsp">Help Center</a></span>' +
		'</div>' + 
		'</div>' +
		'<img class="sub_closeX" src="/images/new/images/sub_popup_close.png" height="32" width="32" title="Close" />' +
		'</div>';

		var HelpPopup = Backbone.View.extend({
			el: $(item),
			template: _.template(tmpl),
			events: {
				'click .popup_close a': 'hide',
				'click .help_popupbox_arrow a': 'openFrame',
				'click .sub_closeX': 'hide',
				'click .help-tooltip': 'show'
			},
			initialize: function(e) {
				this.isActive = false, this.current='', this.frame='', this.winWidth = $(window).width(), that=this;
				this.helpModel = new HelpModel(); 
				this.helpModel.fetch();
				$('body').bind('click',function(e){
					if($(e.target).parents(".help_popupbox").size()==0){
						$('.help_popupbox').remove();
					}
				})

			},
			render: function() {
				return this;
			},
			hide: function(e) {
				$(this.current).remove();
				this.current='';
			},
			hideAll: function(e) {
				if(that.current || that.frame){
					$(that.current).remove();
					$(that.frame).remove();
					that.current = '';
					that.frame='';
				}
			},
			show: function(e) {
				//$('body').unbind('click', this.hideAll);
				this.hideAll();
				e.stopPropagation ? e.stopPropagation() : (e.cancelBubble=true);
				var that = this;	
				_.find(this.helpModel.toJSON().help, function (help) { 
					if(help['id']===e.target.id){
						that.current = $(that.template({ content: help['text'] })).css({top: $(e.target).offset().top - 20, left: $(e.target).offset().left + 16});
						//that.current.css({display: 'block'});
						//that.current.css({display: 'inline-block'});
						$(that.el).append(that.current);
					}
				});
			},
			openFrame: function(e) {
				e.preventDefault();
				e.stopPropagation ? e.stopPropagation() : (e.cancelBubble=true)
				this.hide();
				$(this.el).append(_.template(frameTpl));
				this.$('#help-iframe').attr('src', e.target.href);
				this.frame = this.$('#sub_popupbox').css({left: (this.winWidth/2 - 310), top:(window.pageYOffset==0) ? window.pageYOffset+120 : window.pageYOffset+80});
			}
		});

		return new HelpPopup();
	};
	return exports;
});
