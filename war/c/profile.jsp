<!doctype html>
<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <!--                                                               -->
    <!-- Consider inlining CSS to reduce the number of requested files -->
    <!--                                                               -->
    <link type="text/css" rel="stylesheet" href="CulturalFit.css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
   <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>Cultural Fit: Who you are, not what you are</title>
    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                -->
    <!--                                           -->
    <script type="text/javascript" language="javascript" src="culturalfit/culturalfit.nocache.js"></script>
    <script type="text/javascript">
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
	</script>

  </head>
  <!--                                           -->
  <!-- The body can have arbitrary html, or      -->
  <!-- you can leave the body empty if you want  -->
  <!-- to create a completely dynamic UI.        -->
  <!--                                           -->
  <body>
  	<div id="employerSwitch" class="topright"></div>
	
    <!-- OPTIONAL: include this if you want history support -->
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
    
    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>
	<div id=content></div>
  </body>
  
</html>