<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<%!
    private String findCatolugueById( List data, int id ) {
        IGalleryCatalogue cat = null;
        Iterator it = data.iterator();
        String result = null;
        while( it.hasNext() ) {
            cat = ( IGalleryCatalogue )it.next();
            if( cat.getID() == id ) {
                result = cat.getName();
                break;
            }
        }
        return result;
    }
%>

<%
    if( session.getAttribute( "target" ) == null ) {
%>

<jsp:forward page='<%= ( session.getAttribute( "absolutePath" ) + "/?" + 
    request.getQueryString() ) %>' />

<%
    }
    int catId = 1;
    String idLine = request.getParameter( "id" );
    if( ( idLine != null) && ( idLine ).matches( "[\\d]{1,10}" ) ) {
        catId = Integer.parseInt( idLine );
    }
    boolean isCat = true;
    if( session.getAttribute( "target" ).equals( "pic" ) ) {
        isCat = false;
    }
%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
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
        function getParentID(parentId) {
            var data = parentId.split( '|' );
            var str = data[1];
			window.document.getElementById("parentid").value = data[0];
            window.document.getElementById("parentname").value = str.trim();
		}
        function uploadpic(uploadedurl) {
			window.document.getElementById("url").value = uploadedurl;
            if( window.document.getElementById("url").value != "" ) {
                window.document.getElementById("selectpic").disabled = true;
            }
		}
	</script>
    <script type="text/javascript">
        function send() {
			var pname = window.document.getElementById("parentname");
            var name = window.document.getElementById("name");
            <%= ( !isCat?"var url = window.document.getElementById(\"url\");":"") %>
            if( pname.value == "" || name.value == "" <%= (!isCat)?"|| url.value == \"\" ":"" %>) {
                return false;
            } else {
                window.document.forms['sendform'].submit();
            }
            return true;
		}
	</script>
</head>
<body>

<% 
    if( session.getAttribute( "error" ) != null ) { 
        out.println( session.getAttribute( "error" ) );
        session.setAttribute( "error", null );
%>
<br /><br />
<a href="<%= session.getAttribute( "absolutePath" ) %>">Na glagne</a>

<%
    } else {
        boolean isEdit = true;
        String caption = null;
        String name = null;
        String label = null;
        String desc = null;
        String parentName = null;
        String url = "";
        int parent = 0;
        int id = -1;
        IGalleryCatalogue cat = null;
        IGalleryPicture pic = null;
        if( session.getAttribute( "object" )  == null ) {
            isEdit = false;
        }
        if( isCat ) {
            caption = "Cat";
            label = "Path";
            if( isEdit ) {
                cat = ( IGalleryCatalogue )session.getAttribute( "object" );
                name = cat.getName();
                desc = cat.getDescription();
                id = cat.getID();
                parent = cat.getParent();
                parentName = findCatolugueById( 
                    (List)session.getAttribute( "path" ), parent );
            }
        } else {
            caption = "Pic";
            label = "Cat";
            if( isEdit ) {
                pic = ( IGalleryPicture )session.getAttribute( "object" );
                name = pic.getName();
                desc = pic.getDescription();
                id = pic.getID();
                url = pic.getURL();
                parent = pic.getCatalogue();
                parentName = findCatolugueById( 
                    (List)session.getAttribute( "cats" ), parent );
            }
	    }
%>

<%= caption %>

<form name="sendform" method="post"  action="<%= session.getAttribute( "absolutePath" ) %>/">

name: <input id="name" type="text" name="name" 
value="<%= isEdit?name:"" %>" /><br /><br />

<%= label %><%= ( isEdit?"Edit":"Add" ) %>

<input type="text" id="parentname" 
value="<%= (parentName != null)?parentName:"" %>" readonly />
<input type="button" 
onclick="showPopWin('<%= session.getAttribute( "absolutePath" ) %>/?act=showcat&id=<%= catId %>&cut=y', 300, 600, getParentID);"value="!" />
<br /><br />

<%
        if( !isCat && !isEdit ) {
%>

<input type="button" id="selectpic"
onclick="showPopWin('upload.jsp', 400, 200, uploadpic);" value="Choose pic" />
<br /><br />

<%
        }    
%>

<input type="hidden" name="mode" value="<%= isEdit?"edit":"add" %>" />
<input type="hidden" name="target" value="<%= isCat?"cat":"pic" %>" />
<input type="hidden" name="id" value="<%= id %>" />
<input type="hidden" name="url" value="<%= ( !isCat )?url:"" %>" />
<input type="hidden" id="parentid" name="parent" value="<%= parent %>" />

Desc
<textarea name="desc"><%= isEdit?desc:"" %></textarea><br /><br />

<input value="Ok" type="button" onclick="send();" />

<input type="button" value="Cancel" 
onclick="window.location.href='<%= session.getAttribute( "absolutePath" ) %>'" />

</form>

<%
        session.setAttribute( "target", null );
    }
%>

</body>
</html>