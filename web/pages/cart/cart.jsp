<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%--静态包含 base标签，css样式，jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			//给删除操作绑定单击事件
			$("a.deleteItem").click(function () {
				return confirm("确定要删除《"+ $(this).parent().parent().find("td:first").text()+"》吗？");
			});

			//给清空操作绑定单击事件
			$("a.clear").click(function () {
				return confirm("确定要清空购物车吗？");
			});

			//给输入框绑定内容发生改变事件
			$("input.updateCount").change(function () {
				//获取商品名称
				var name=$(this).parent().parent().find("td:first").text();
				//获取商品数量
				var count=$(this).val();
				//根据属性bookId，获取商品id
				var id = $(this).attr("bookId");
				if(confirm("你确定要将《"+name+ "》的数量修改为 <"+count+">吗？")) {
					//向服务器发起修改请求
					location.href="${bastPath}cartServlet?action=updateCount&count="+count+"&id="+id;
				}else {
					//取消则，还原原来的数量
					//defaultValue属性是 表单项DOM对象的属性，表示默认的value值
					this.value=this.defaultValue;
				}
			});
		});
	</script>
</head>
<body>
	
	<div id="header">
			<%--<img class="logo_img" alt="" src="static/img/logo.gif" >--%>
			<span class="wel_word">购物车</span>
			<%--静态包含，登陆成功之后的菜单--%>
			<%@include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<%--购物车为空的情况--%>
			<c:if test="${empty sessionScope.cart.items}">
				<tr>
					<td colspan="5"><a href="index.jsp">购物车为空！快去购物吧</a></td>
				</tr>
			</c:if>
			<%--购物车不空时，输出内容--%>
			<c:if test="${not empty sessionScope.cart.items}">
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>
							<input class="updateCount" style="width: 80px" type="text"
								   bookId="${entry.value.id}"
								   value="${entry.value.count}" >
						</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${entry.value.id}">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>

		</table>


		<%--购物车非空才输出下面的内容--%>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
				<span class="cart_span"><a class="clear" href="cartServlet?action=clear">清空购物车</a></span>
				<span class="cart_span"><a  href="orderServlet?action=createOrder">去结账</a></span>
			</div>
		</c:if>
	
	</div>

	<%--静态包含页脚内容--%>
	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>