/** sql-ibator author : tianyao **/
sql-ibator功能:通过sql文件进行逆向工程
1. sql.txt 为数据库导出的sql文件(会读取以.sql结尾的文件)
2. 配置存放在tibatis.properties文件
3. 执行java -jar sql-ibator-1.6.0.jar即可
4. 运行日志存放在logs/debug.yyyyMMdd.log文件
5. serializable.properties为序列化的配置，保证每次生成的实体类的序列化号保持一致(一般不需要手动维护)
6. 1.5.9重构代码，修复bug
7. 1.6.0后加入版本概念，其依赖history.json(勿删，勿改)