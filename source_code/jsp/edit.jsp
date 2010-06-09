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
    int catId = 1;
    String idLine = request.getParameter( "id" );
    if( ( idLine != null) && ( idLine ).matches( "[\\d]{1,10}" ) ) {
        catId = Integer.parseInt( idLine );
    }
    boolean isCat = true;
    if( session.getAttribute( "target" ).equals( "pic" ) ) {
        isCat = false;
    }
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
    String thumb = "img/none.gif";
    if( session.getAttribute( "object" )  == null ) {
        isEdit = false;
    }
    if( isCat ) {
        caption = "Category";
        label = "Path";
        if( isEdit ) {
            cat = ( IGalleryCatalogue )session.getAttribute( "object" );
            name = cat.getName();
            desc = cat.getDescription();
            id = cat.getID();
            parent = cat.getParent();
            parentName = findCatolugueById( 
                (List)session.getAttribute( "path" ), parent );
        } else {
            parent = catId;
            parentName = findCatolugueById( 
                (List)session.getAttribute( "path" ), catId );
        }
    } else {
        caption = "Picture";
        label = "Cat";
        if( isEdit ) {
            pic = ( IGalleryPicture )session.getAttribute( "object" );
            name = pic.getName();
            desc = pic.getDescription();
            id = pic.getID();
            url = pic.getURL();
            thumb = "files/" + ( new File( url ) ).getName();
            parent = pic.getCatalogue();
            parentName = findCatolugueById( 
                (List)session.getAttribute( "cats" ), parent );
        } else {
            parent = catId;
            parentName = findCatolugueById( 
                (List)session.getAttribute( "path" ), catId );
        }
    }
%>

<form name="sendform" method="post"  action="<%= session.getAttribute( "absolutePath" ) %>/">

<table border="0" width="950" cellspacing="0" cellpadding="10"><tr>
<td>

<b><%= caption %> <%= ( isEdit?"editing":"adding" ) %></b><br /><br />

<table border="0" cellspacing="0" cellpadding="0">
<tr><td width="150">Name:</td><td><input id="name" type="text" name="name" value="<%= isEdit?name:"" %>" /></td></tr>
<tr><td><%= label %></td><td><input type="text" id="parentname" value="<%= (parentName != null)?parentName:"" %>" readonly />
<input type="button" 
    onclick="showPopWin('<%= session.getAttribute( "absolutePath" ) %>/?act=showcat&id=<%= isEdit?parent:catId %>&cut=y', 300, 600, getParentID);"
    value="select" /></td></tr>

<%
        if( !isCat ) {
%>
<tr><td>Thumb:</td><td>
<img src="<%= session.getAttribute( "absolutePath" ) %>/<%= thumb %>" 
    id="thumb" width="100" height="100" border="1">

<input type="button" id="selectpic"
onclick="showPopWin('upload.jsp', 400, 100, uploadpic);" value="select" />
</td></tr>

<%
        }    
%>

<input type="hidden" name="mode" value="<%= isEdit?"edit":"add" %>" />
<input type="hidden" name="target" value="<%= isCat?"cat":"pic" %>" />
<input type="hidden" name="id" value="<%= id %>" />
<input type="hidden" id="url" name="url" value="<%= ( !isCat )?url:"" %>" />
<input type="hidden" id="parentid" name="parent" value="<%= parent %>" />

<tr><td>Desc</td><td><textarea name="desc"><%= isEdit?desc:"" %></textarea></td></tr>

<tr><td colspan="2">&nbsp;</td></tr>

<tr><td align="right"><input value="Ok" type="button" onclick="send();" />
</td><td>
<input type="button" value="Cancel" 
onclick="window.location.href='<%= session.getAttribute( "absolutePath" ) + "/?act=showcat&id=" 
%><%= parent %>'" />
</td></tr>
</table>
</td>
</tr></table>
</form>
