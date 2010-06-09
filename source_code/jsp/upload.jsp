<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript">
		function hide() {
            var url = window.document.getElementById("url");
            if( url != null ) {
                returnVal = url.value;
            } else {
                returnVal = "";
            }
			window.top.hidePopWin(true);
		}
	</script>
</head>

<%
    if( session.getAttribute( "uploadedpic" ) == null ) {
%>

<body>
<form enctype="multipart/form-data" method="post"
action="<%= session.getAttribute( "absolutePath" ) %>/" >

Select picture:<br />

<input type="file" name="path" /><br /><br />

<input value="Ok" type="submit" />

<input type="button" value="Cancel" 
onclick="hide();" />

</form>

<%
    } else {
%>

<body onload="hide();">
<input type="hidden" id="url" 
value="<%= ( ( IGalleryPicture )session.getAttribute( "uploadedpic" ) ).getURL() %>" />
<%
        session.setAttribute( "uploadedpic", null );
    }    
%>


</body>
</html>