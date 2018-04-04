<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>waitting</title>
</head>
<body>
    正在支付，不存在form重复提交
    <br>
    ${name}${price}块钱买了一个${product}，现在用
        <#if payMethod??>
            <#if payMethod == 'alipay'>
                支付宝
            <#elseif payMethod == 'wxpay'>
                微信
            </#if>
        </#if>
    支付
</body>
</html>