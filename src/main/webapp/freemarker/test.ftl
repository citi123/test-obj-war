<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	qqqqqqqqqqqqq
	
	<br>
	
	${name}
	
	<br>
	
	<#if name != '张三'>
		you are a small b
	<#elseif name == '李四'>
		hehe,xiao li
	<#else>
		${name}
	</#if>
	
	
	<br>
	<#list nameList as name>
		${name} <#sep>,
	<#else>
		不存在用户名称
	</#list>
	
	<br>
	
	<#list nameList>
		<#items as name>
			${name} <#sep>,
		</#items>
	<#else>
		不存在用户名称
	</#list>
	
	
	<br>
	${adfd!"变量不存在"} 
	${(a.b.c.e)!0}
	<#if adfd??>
		${adsd}
	<#else>
		adsd 不存在
	</#if>
	
	
	${"It's \"quoted\" and
	this is a backslash: \\"}
	
	<br>
	
	<#assign a = "true"/>
	${a?boolean}<br>
	<#assign a = "qwe rty ui op"/>
	${a?cap_first}<br>
	${a?capitalize}<br>
	${a?contains("qwe r")}
	
	<br>
	${date?date}<br>
	${date?time}<br>
	${date?datetime}<br>

	<#include "footer.ftl"/>
	
</body>
</html>