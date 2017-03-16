# Mopassion

### Outsourcing， 接的是一个外国的项目。
需要我这边实现一个类似 大陆的 “陌陌” App 功能：

### 功能方面： 
登陆注册(包括Twitter和Facebook授权)、定位、浏览附近的动态、浏览附近的人、好友功能（关注和粉丝）、以及聊天功能

### 技术方面：
全程用了MVP模式，并且做了很好的封装，可以查阅代码 <br /> 
网络连接用的是Mina (基于Java Socket）<br />
通讯协议是Protobuf <br />
定位是高德LBS <br />
本地数据库用ORMLite <br />
写了一个比较强大的工具库：libraries\commonutils <br />
组件通讯是EventBus <br />
图片库是Fresco <br />
依赖注入是ButterKnife <br />
Twitter 和 Facebook 的认证库 <br />


### PS: 但是这个项目由于老外运营方面出现问题，上不了线。但是我这边的项目功能是没有问题的，也如期交付给他们。
