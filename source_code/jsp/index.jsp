<%@ page contentType="text/html; charset=UTF-8" %>

<%
    if( session.getAttribute( "target" ) == null ) {
%>

<jsp:forward page='<%= ( session.getAttribute( "absolutePath" ) + "/" + 
    ( ( request.getQueryString() != null && !request.getQueryString().equals( "" ) 
    )?( "?" + request.getQueryString() ):"" ) ) %>' />

<%
    }
    boolean isIncEdit = false;
    if( request.getParameter( "act" ) != null ) {
        if( request.getParameter( "act" ).equals( "editpic" ) || 
                request.getParameter( "act" ).equals( "editcat" ) || 
                request.getParameter( "act" ).equals( "addcat" ) || 
                request.getParameter( "act" ).equals( "addpic" ) ) {
            isIncEdit = true;
        }
    }
%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css" />

<%
    if ( isIncEdit ) {
%>

	<link rel="stylesheet" type="text/css" href="css/subModal.css" />
	<script type="text/javascript" src="scripts/common.js"></script>
	<script type="text/javascript" src="scripts/subModal.js"></script>
    <script  type="text/javascript">	
		String.prototype.trim = function() {
			return this.replace(/^[\s|\xA0]+|[\s|\xA0]+$/g,"");
		}
		String.prototype.ltrim = function() {
			return this.replace(/^[\s|\xA0]+/,"");
		}
		String.prototype.rtrim = function() {
			return this.replace(/[\s|\xA0]+$/,"");
		}
	</script>
    <script type="text/javascript">
        function showThumb() {
            var url = window.document.getElementById("url");
            if( url != null ) {
                var str = url.value;
                if( str != "" ) {
                    var data = str.split( /[\\|\/]/ );
                    var len = data.length;
                    window.document.getElementById("thumb").src = '<%= 
                    session.getAttribute( "absolutePath" ) %>/files/' + data[len - 1];
                }
            }
        }
    </script>
	<script type="text/javascript">
        function getParentID(parentId) {
            var data = parentId.split( '|' );
            var str = data[1];
			window.document.getElementById("parentid").value = data[0];
            window.document.getElementById("parentname").value = str.trim();
		}
        function uploadpic(uploadedurl) {
			window.document.getElementById("url").value = uploadedurl;
            showThumb();
		}
	</script>
    <script type="text/javascript">
        function send() {
			var pname = window.document.getElementById("parentname");
            var name = window.document.getElementById("name");
            <%= ( ( session.getAttribute( "target" ) != null ) && ( session.getAttribute( "target" )
                .equals( "pic" ) ) )?"var url = window.document.getElementById(\"url\");":"" %>
            if( pname.value == "" || name.value == "" <%= ( ( session.getAttribute( "target" ) !=
                null ) && ( session.getAttribute( "target" ).equals( "pic" ) 
                ) )?"|| url.value == \"\" ":"" %>) {
                return false;
            } else {
                window.document.forms['sendform'].submit();
            }
            return true;
		}
	</script>

<%
    }
%>

</head>
<body bgcolor="#dddddd">

<% 
    if( session.getAttribute( "error" ) != null ) { 
        out.println( "<b>Error!</b>" );
        out.println( "<pre>" );
        out.println( session.getAttribute( "error" ) );
        out.println( "</pre>" );
        session.setAttribute( "error", null );
%>
<br /><br />
<a href="<%= session.getAttribute( "absolutePath" ) %>"><u>Back to main page.</u></a>

<%
    } else {
%>

<table border="1" class="tbl" width="950" align="center" cellspacing="2" cellpadding="0" bgcolor="white">
<tr>
    <td colspan="2" class="tbl" align="center">
        <font class="header">Picture Gallery</font>
    </td>
</tr>

<%
        String targId = "";
        if( session.getAttribute( "target" ).equals( "pic" ) ) {
            targId = "&id=" + String.valueOf( ( ( IGalleryCatalogue )( 
                ( List )session.getAttribute( "path" ) ).get( 0 ) ).getID() );
        } else {
            if( request.getParameter( "id" ) != null ) {
                targId = "&id=" + request.getParameter( "id" );
            }
        }
%>

<tr>
    <td colspan="2" class="tbl" align="center">
        <table border="0">
            <tr><td><img src="img/addcat.png" border="0"></td><td>
            <a href="<%= session.getAttribute( "absolutePath" ) + 
                "/?act=addcat" + targId %>"> [add category]</a>
            </td><td width="20">&nbsp;
            </td><td><img src="img/addpic.png" border="0">
            </td><td>
            <a href="<%= session.getAttribute( "absolutePath" ) + "/?act=addpic" + targId %>">[add picture]</a>
            </td></tr>
        </table>
    </td>
</tr>

<tr valign="top">
<td class="tbl">

<%      
        if ( isIncEdit ) { 
%>

<%@include file="edit.jsp" %>

<%
        } else {
%>

<%@include file="view.jsp" %>
<%
        }
%>

</td>
</tr>

<tr>
<td colspan="2" class="tbl" align="center">
<font class="footer">&copy; Group8 2010</font>
</td>
</tr>
</table>

<%
        session.setAttribute( "target", null );
    }
%>

</body>
</html>