<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <%--静态包含 base标签，css样式，jQuery文件--%>
    <%@include file="/pages/common/head.jsp" %>
    <script type="text/javascript">
        $(function () {
            // 给删除的a标签绑定单击事件，用于删除的确认提醒操作
            $("a.deleteClass").click(function () {
                /**
                 * confirm是确认提示框函数
                 * 参数是提示的内容
                 * 它的按钮有两个，一个是确认，一个是删除
                 * 返回true表示确认；返回false表示取消
                 */

                return confirm("确定要删除【" + $(this).parent().parent().find("td:first").text() + "】？");
            });

            //给跳转指定页面的按钮绑定单击事件
            $("#searchPageBtn").click(function () {
                //获取输入框的内容
                var pageNo = $("#pn_input").val();

                //javaScript语言中提供了一个location地址栏对象
                //它有一个属性 href，可以获取浏览器地址栏中的地址
                // href属性可读可写,赋值时就是页面跳转了

                //数据校验，不能跳转到负数的页码
                if (pageNo > 1 && pageNo <=${requestScope.page.pageTotal}) {
                    location.href = "${requestScope.page.url}&pageNo=" + pageNo;
                }else {
                    location.href = "${requestScope.page.url}&pageNo=" + ${requestScope.page.pageNo};
                }

            });
        });
    </script>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
    <span class="wel_word">图书管理系统</span>
    <%--静态包含 manger管理模块的菜单--%>
    <%@include file="/pages/common/manger_menu.jsp" %>
</div>

<div id="main">
    <table>
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td colspan="2">操作</td>
        </tr>

        <c:forEach items="${requestScope.page.items}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td>${book.author}</td>
                <td>${book.sales}</td>
                <td>${book.stock}</td>
                <td><a href="manager/bookServlet?action=getBook&id=${book.id}&pageNo=${requestScope.page.pageNo}">修改</a></td>
                <td><a class="deleteClass" href="manager/bookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
            </tr>
        </c:forEach>


        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_edit.jsp?pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
        </tr>
    </table>

    <%@include file="/pages/common/page_nav.jsp"%>

</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp" %>
</body>
</html>