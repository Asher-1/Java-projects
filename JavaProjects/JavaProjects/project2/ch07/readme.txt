The import org.eclipse cannot be resolved
则表示需要安装SWT类库,下载SWT类库,然后将其加入CLASSPATH
如果是在eclipse中开发，你需要在你的工程构建路径中加入swt.jar的路径，在运行中的jvm参数中加入
-Djava.library.path="dll所在目录的绝对路径" 

工程-->右键属性-->java构建路径-->库-->添加外部jar-->添加swt.jar（自己找找在哪儿）

