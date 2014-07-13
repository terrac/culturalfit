<%@ page import="com.caines.cultural.server.*" %>
<!doctype html>
<html>
  <head>
    <meta http-equiv=content-type content=text/html; charset=UTF-8>
    <link type=text/css rel=stylesheet href=/assets/CulturalFit.css>
    <link href=/assets/bootstrap.min.css rel=stylesheet>
    <script src=/assets/bootstrap.min.js></script>
    <title>Cultural Fit: Who you are, not what you are</title>
    <script type=text/javascript language=javascript src=/assets/CulturalFit.js></script>
  </head>                                       
  <body>
	<button onclick="myFunction()">Contact this person</button>
	<a href="/welcome.html">Back to entrance</a>
  	<ul class="list-group">
	  <%=JUtil.showList()%></li>
	</ul>

	<script>
	function myFunction() {
	    alert("Not implemented yet, will cost 10 dollars");
	}
	</script>
  </body>
</html>