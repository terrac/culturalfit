function addLink() {
		    var body_element = document.getElementsByTagName('body')[0];
		    var selection;
		    selection = window.getSelection();
		    var pagelink = "<br /><br /> Read more at: <a href='"+document.location.href+"'>"+document.location.href+"</a><br />Copyright &copy; c.bavota"; // change this if you want
		    var copytext = selection + pagelink;
		    var newdiv = document.createElement('div');
		    newdiv.style.position='absolute';
		    newdiv.style.left='-99999px';
		    body_element.appendChild(newdiv);
		    newdiv.innerHTML = "";
		    selection.selectAllChildren(newdiv);
		    window.setTimeout(function() {
		        body_element.removeChild(newdiv);
		    },0);
		}
		function setupSelectNothing(){
			document.body.oncopy = addLink;
		}
		
		function setCopy(text) {
		    var body_element = document.getElementsByTagName('body')[0];
		    var selection;
		    selection = window.getSelection();
		    var newdiv = document.createElement('div');
		    newdiv.style.position='absolute';
		    newdiv.style.left='-99999px';
		    body_element.appendChild(newdiv);
		    newdiv.innerHTML = text;
		    selection.selectAllChildren(newdiv);
		    window.setTimeout(function() {
		        body_element.removeChild(newdiv);
		    },0);
		}