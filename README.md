## 说明

Project 测试示例

	|— tannotation ：注解

	|— compiler ：编译时运行逻辑

	|— customer ：测试项目


annotationprocessing101 项目clone自：[Java注解处理器](https://www.race604.com/annotation-processing/)

感谢该作者的分享！


## 使用

1. Project中的模块按以上列出顺序执行 `mvn clean install`
2. 在编译 `customer` 模块时，JVM会自动执行 `compiler` 中的代码，生成代码

> annotationprocessing101 类似

