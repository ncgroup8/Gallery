<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<html>
<head>
    <script type="text/javascript">
		function hide() {
            var to = window.document.getElementById("cats");
			var temp1 = to.value;
			var temp2 = to.options[to.selectedIndex].text;
			returnVal = temp1 + "|" + temp2;
			window.top.hidePopWin(true);
		}
	</script>
</head>
<body>

<%!
    private String createTree( List data, int node, String pref, String tab ) {
        StringBuffer sb = new StringBuffer( "" );
        IGalleryCatalogue cat = null;
        Iterator it = data.iterator();
        while( it.hasNext() ) {
            cat = ( IGalleryCatalogue )it.next();
            if( cat.getParent() == node ) {
                sb.append( "<option value=\"" ).append( cat.getID() ).
                    append( "\">" ).append( pref ).append( cat.getName() ).
                    append( "</option>\n" ).
                    append( createTree( data, cat.getID(), (pref + tab), tab ) );
            }
        }
        return sb.toString();
    }
%>

<%
    if( session.getAttribute( "cats" ) == null ) {
        out.println( "<b>Something wrong! It must be at least root directory</b>" );
    } else {
%>



<select name="cats" id="cats" size="5">

<%
        List path = (List)session.getAttribute( "cats" );
        out.println( createTree( path, 0, "", "&nbsp;&nbsp;&nbsp;&nbsp;" ) );
%>

</select><br /><br />

<input type="button" onclick="hide();" value="Ok" />

<%
    }
%>

</body>
</html>