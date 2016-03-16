# 更新进度

## 2016.3.16

- 模拟访问获取账单数据接口
- 给 ListView 设置适配器

## 2016.3.15

- 添加 url.xml 文件，并以 XML 格式记录服务器接口信息，每一个接口信息对应一个 UrlData 对象，通过 UrlConfigManager 读取 url.xml 文件得到
- 增加网络请求参数类型 RequestParameter
- 使用 okHttp 开源项目进行网络访问，并对其中的 get 和 post 请求做了简单封装，所有网络访问都通过 RemoteService 类进行

## 2016.3.13

- 创建项目
- 闪屏界面
- 增加主页菜单：概况、账单、我
- 模拟登录接口

