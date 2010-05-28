<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<html>
<head>
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
<body>

<%
    if( session.getAttribute( "uploadedpic" ) == null ) {
%>

<form enctype="multipart/form-data" method="post"
action="<%= session.getAttribute( "absolutePath" ) %>/" >

<input type="file" name="path" /><br /><br />

<input value="Ok" type="submit" />

<input type="button" value="Cancel" 
onclick="hide();" />

</form>

<%
    } else {
%>

<input type="hidden" name="url" 
value="<%= ( ( IGalleryPicture )session.getAttribute( "uploadedpic" ) ).getURL() %>" />

Pic Successfully uploaded!

<input value="Ok" type="button" onclick="hide();" />

<%
        session.setAttribute( "uploadedpic", null );
    }    
%>


</body>
</html>