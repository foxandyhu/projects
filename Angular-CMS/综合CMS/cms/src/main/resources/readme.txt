该程序包含用户注册，会员管理，站内信功能等
ext 不用上传到git 存放不同环境的配置文件
maven 打包：
    测试环境:mvn clean package -P test
    正式环境:mvn clean package -P prod