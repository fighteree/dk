const {
  defineConfig
} = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 80, //设置端口,
    proxy: {
      '/api': { //所有以/api开头的请求都以代理服务器发送
        target: 'http://localhost:8080', // 目标服务器的地址，应该写得和你本地配置一样
        changeOringin: true, //是否跨域请求
        pathRewrite: {
          '^/api': '/' //代理服务器转发时是否需要改写 URI，以及如何改写。
        }
      }
    }
  }
})