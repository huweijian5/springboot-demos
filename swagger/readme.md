# swagger-ui示例
* 工程启动后通过 http://${host}:${port}/v2/api-docs 你可以获得swagger json字符串，这串东西就可以在swagger的官网上变成可视的接口文档，当然，你也可以导入到postman,但postman对其的支持似乎还比较差
* 由于引入了swagger-ui,因此当工程启动后你可以通过http://${host}:${port}/swagger-ui.html在线查看接口文档
* 注意：线上环境应该把接口文档关闭
* swagger-bootstrap-ui可以用来替换默认的ui,访问地址http://${host}:${port}/doc.html