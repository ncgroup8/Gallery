<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="ua.sumdu.group8.Gallery.*" %>

<%! final int ELEMENTS_IN_ROW = 6; %>
<%! final int SHOWPIC_COLS = 2; %>
<%! final int SHOWPIC_ROWS = 2; %>

<%
    boolean isSelect = false;
    if( session.getAttribute( "cut" ) != null ) {
        if( session.getAttribute( "cut" ).equals( "y" ) ) {
            isSelect = true;
        }
    }
    if( ( session.getAttribute( "target" ) == null ) && !isSelect ) {
%>

<jsp:forward page='<%= session.getAttribute( "absolutePath" ) + "/?act=showcat&id=1&cut=y" %>' />

<%
    }
%>


<% 
    if( isSelect ) {
%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript">
        function send() {
			var rad = window.document.forms['selform'].selcat;
			var ind = -1;
			for( i = 0; i < rad.length; i++ ) {
				if( rad[i].checked ) {
					ind = i;
					break;
				}
			}
			if( ind == -1) {
				return false;
			}
            returnVal = rad[ind].value ;
            window.top.hidePopWin(true);
		}
        function addhere() {
			returnVal = window.document.getElementById("url").value;
            window.top.hidePopWin(true);
		}
	</script>
</head>
<body>

<%
    } 
%>

<% 
    boolean isShowCat = true; 
    if( session.getAttribute( "target" ).equals( "pic" ) && !isSelect ) {
        isShowCat = false;
    }
%>

<table border="0" width="<%= isSelect?250:950 %>" cellspacing="0" cellpadding="10">
<tr>
<td colspan="<%= isSelect?1:(isShowCat?ELEMENTS_IN_ROW:SHOWPIC_COLS) %>">

<%
    List path = ( List )session.getAttribute( "path" );
    Iterator it = null;
    String fullPath = null;
    IGalleryCatalogue cat = null;
    IGalleryPicture pic = null;
    if( path == null ) {
        out.println( "<b>Wrong path!</b>" );
    } else {
        it = path.iterator();
        StringBuffer sb1 = new StringBuffer( "" );
        String str2 = "";
        String str = null;
        StringBuffer sb2 = new StringBuffer( "" );
        String str4 = "";
        String str3 = null;
        while( it.hasNext() ) {
            cat = ( IGalleryCatalogue )it.next();
            sb1.setLength( 0 );
            sb1.append( "<b><a href=\"" ).append( session.
                getAttribute( "absolutePath" ) ).append( "/?act=showcat&id=" ).
                append( cat.getID() ).append( isSelect?"&cut=y":"" ).
                append( "\">" ).append( cat.getName() ).append( "</a></b> > " );
            str = sb1.toString();
            str2 = str + str2;
            sb2.setLength( 0 );
            sb2.append( cat.getName() ).append( "/" );
            str3 = sb2.toString();
            str4 = str3 + str4;
        }
        out.println( str2 );
        fullPath = str4;
        if( !isShowCat ) {
            pic = ( IGalleryPicture )session.getAttribute( "pic" );
            out.println( pic.getName() );
        }
    }
%>

<hr width="100%" size="1" color="#dddddd" />

</td>
</tr>

<%
    if( isShowCat ) {
%>

<% 
        int c = 0;
        List cats = ( List )session.getAttribute( "cats" );
        List pics = ( List )session.getAttribute( "pics" );
        if( ( cats == null ) && ( ( pics == null) || isSelect ) ) {
%>

<tr>
<td colspan="<%= ELEMENTS_IN_ROW %>">
<b>Directory is empty!</b>
<br /><br />

<%
            if( isSelect ) {
%>

<input type="hidden" id="url" value="<%= ( 
    ( IGalleryCatalogue )path.get( 0 ) ).getID() %>|<%= fullPath %>" />
<input type="button" value="select" onclick="addhere();"><br />

<%
            }    
%>

</td>
</tr>

<%
        } else {
            if( isSelect ) {
%>

<tr>
<td>
<form name="selform" action="#">
<input type="radio" name="selcat" 
value="<%= ( ( IGalleryCatalogue )path.get( 0 ) ).getID() %>|<%= fullPath %>" />

<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=showcat&id=<%= 
    ( ( IGalleryCatalogue )path.get( 0 ) ).getID() %>&cut=y">
<img src="img/smallcat.png" border="0" />
<font style="font-size:18px;"><u>
<%= ( ( IGalleryCatalogue )path.get( 0 ) ).getName() %></u></font>
(current)</a>

<br />

<hr width="100%" size="1" color="#222222" />


<%
            }
            if( cats != null ) {
                it = cats.iterator();
                while ( it.hasNext() ) { 
                    cat = ( IGalleryCatalogue )it.next();
                    if( !isSelect ) {
                        if( (c % ELEMENTS_IN_ROW) == 0 ) {
%>

<tr>

<%
                        }
%>

<td>

<% 
                    } else {
%>

<input type="radio" name="selcat" value="<%= cat.getID() %>|<%= fullPath %><%= cat.getName() %>/" />

<%
                    }
%>

<a href="<%= session.getAttribute( "absolutePath" ) 
    %>/?act=showcat&id=<%= cat.getID() 
    %><%= isSelect?"&cut=y":"" %>">
<img src="img/<%= isSelect?"small":"" %>cat.png" border="0" />

<%
                    if( !isSelect ) {
%>

<br />

<%
                    }    
%>    

<font style="font-size:18px;"><%= cat.getName() %></font>
</a>
<hr width="100%" size="1" color="#dddddd" />

<%
                    if( !isSelect ) {
%>

<table border="0">
<tr><td><img src="img/edit.png" border="0"></td><td>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=editcat&id=<%= cat.getID() %>">[edit]</a></td><td width="10">
</td><td><img src="img/del.png" border="0"></td><td>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=delcat&id=<%= cat.getID() %>">[del]</a></td><td>
</tr>
</table>

</td>

<%
                     }
                    if( !isSelect && (c % ELEMENTS_IN_ROW) == (ELEMENTS_IN_ROW - 1) ) {
%>

</tr>

<%
                    }
                    c++;
                }
            }
            if( isSelect ) {
%>

<br /><br />
<input type="button" value="select" onclick="send();"><br />
</form>
<td>
</tr>

<%
            }
            if( !isSelect && pics != null ) {
                it = pics.iterator();
                while ( it.hasNext() ) { 
                    pic = ( IGalleryPicture )it.next();
                    if( (c % ELEMENTS_IN_ROW) == 0) {
%>

<tr>

<%
                    }
%>

<td>

<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=showpic&id=<%= pic.getID() %>">
<img src="img/pic.png" border="0" /><br />

<font style="font-size:18px;"><%= pic.getName() %></font>
</a>
<hr width="100%" size="1" color="#dddddd" />

<table border="0">
<tr><td><img src="img/edit.png" border="0"></td><td>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=editpic&id=<%= pic.getID() %>">[edit]</a></td><td width="10">
</td><td><img src="img/del.png" border="0"></td><td>
<a href="<%= session.getAttribute( "absolutePath" ) %>/?act=delpic&id=<%= pic.getID() %>">[del]</a></td><td>
</tr>
</table>

</td>

<% 
                    if( (c % ELEMENTS_IN_ROW) == (ELEMENTS_IN_ROW - 1) ) {
%>

</tr>

<%
                    }
                    c++;
                }
            }
            if( ((c % ELEMENTS_IN_ROW) != 0) && !isSelect)
            {
                while ( (c % ELEMENTS_IN_ROW) !=0 )
                {
%>

<td></td>

<%
                    c++;
                }
%>

</tr>

<%
            }
        }
    } else {
%>

<tr valign="top">
<td>
<img src="<%= session.getAttribute( "absolutePath" ) + "/files/" +
    ( new File( pic.getURL() ).getName() ) %>" />
</td>
<td width="200"><b>Name:</b> 
<%= pic.getName() %>
<br /><br />
<b>Description:</b><br /> 
<%= pic.getDescription() %>
</td>
</tr>

<%
    }
%>

</table>

<% 
    if( isSelect ) {
%>

</body>
</html>

<%
        session.setAttribute( "cut", null );
    } 
%>