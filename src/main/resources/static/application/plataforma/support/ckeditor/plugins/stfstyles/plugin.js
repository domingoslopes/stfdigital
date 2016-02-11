(function() {
	CKEDITOR.plugins.add('stfstyles', {
		icons : 'marker,smallcaps',
		init : function(editor) {
			var style = new CKEDITOR.style({
				element : 'span',
				attributes : {
					'class' : 'marker'
				}
			});

			editor.attachStyleStateChange(style, function(state) {
				!editor.readOnly && editor.getCommand('marker').setState(state);
			});

			editor.addCommand('marker', new CKEDITOR.styleCommand(style));

			editor.ui.addButton('Marker', {
				label : 'Mark',
				command : 'marker',
				toolbar : 'insert'
			});
			
			style = new CKEDITOR.style({
				element : 'span',
				attributes : {
					'class' : 'smallcaps'
				}
			});
			
			editor.attachStyleStateChange(style, function(state) {
				!editor.readOnly && editor.getCommand('smallcaps').setState(state);
			});
			
			editor.addCommand('smallcaps', new CKEDITOR.styleCommand(style));
			
			editor.ui.addButton('Smallcaps', {
				label : 'Smallcaps',
				command : 'smallcaps',
				toolbar : 'insert'
			});
		}
	});
})();