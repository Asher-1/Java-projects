
Ch01：俄罗斯方块游戏的实现
    安装JDK，并正确配置环境变量（如果有疑问，可以参考任何一本java入门书籍）。打开Eclipse（可以到http://www.eclipse.org/现在官方最新版，Eclipse本身是免费软件），导入ch01下的java项目，然后保存、运行game.ErsBlocksGame类即可。


Ch02：俄罗斯方块双人对战的实现
    安装JDK，并配置环境变量，打开Eclipse，导入java项目ch02，然后保存、运行game.ErsBlocksGame 类即可。


Ch03：五子棋对战游戏的实现
    安装JDK、配置环境变量，打开Eclipse导入ch03项目，运行server.chessServer类启动服务器端，运行client.chessClient类启动客户端。目前只支持两个客户端进行网络对战，多个玩家还有如线程同步问题等。两个客户端可以在同一台电脑上运行。


Ch04：多媒体展示系统 –网络游鱼
    安装JDK、配置环境变量，打开Eclipse导入ch04项目，运行server.FishServer类启动服务器端，运行java client.SwimFish类启动客户端。注意，同时在多台机上运行客户端才能显示出网络游鱼的效果，在同一台机上打开多个客户端窗口不能看到该效果。
  

Ch05：野人与传教士过河问题的实现
    安装JDK、配置环境变量，打开Eclipse导入ch05项目，运行主类crossriver.CrossRiver即可启动演示程序。


Ch06：人机对战五子棋游戏的实现
    安装JDK，配置环境变量，双击compile.bat 批处理文件编译项目，双击runFive.bat批处理文件可启动项目。


Ch07：蚁群算法的模拟实现
    安装JDK，配置环境变量，导入ch07项目，一般情况下直接保存、运行ant.UI即可启动程序。如果不能运行，将ch07文件夹下的swt.jar加入构建路径，然后在运行中的JVM参数中加入-Djava.library.path="dll所在目录的绝对路径"即可。
也可以在命令行下直接运行该程序，假设ch07文件夹放在D盘跟目录。则在cmd下切换到ch07文件夹下，运行如下命令即可启动程序：
    
    java  -cp D:\ch07\swt.jar;.;  -Djava.library.path=D:\ch07  ant.UI


ch08：SchoolEbay的实现
    安装JDK，配置环境变量，安装ant和tomcat，并配置相应环境变量，不清楚的读者可以参见其文档。安装SQL Server 2000或更高版本，注意SQL Server 2000必须打sp3补丁，否则无法监听端口，客户端不能连接。
然后SchoolEbay\database目录下的数据库文件附加到Sql Server，具体操作是打开企业管理器，在左边的树状列表中右击数据库，在弹出的快捷菜单中选择“所有任务”|“附加数据库”命令，再选择要附加的数据库文件即可。
打开控制台（运行中输入cmd），将目录转到SchoolEbay所在目录，输入ant命令来编译打包程序（编译前请修改datasource下的Constants.java文件，将其中的username和passoword修改为SQL Server的用户名和密码）。将生成的SchoolEbay.war添加到Tomcat的webapps目录下，并启动Tomcat、启动Sql Server。访问http://localhost:8080/SchoolEbay/index.jsp，可启动本项目。


Ch09：Ajax技术在网上教学平台的应用
    安装JDK，配置环境变量，安装Tomcat、SQL Server并进行相应配置。创建数据库Ajax，然后使用sql目录下的ajaxbak文件还原数据库。为Eclipse安装MyEclipse插件。使用Eclipse导入项目ch09, 在src/config.properties中配置数据库连接信息, 然后部署项目、启动tomcat。
访问http://localhost:8080/ajax即可启动本程序。


Ch10：一个简单的编译器实现
    安装JDK、配置环境变量，启动Eclipse并导入ch10项目，运行主类calculator.Calculator即可。


Ch11：基于RMI分布计算实例
    安装JDK、配置环境变量，编译java源文件（可以用eclipse自动编译也可以手动命令行下编译），然后命令行下运行如下命令启动服务器端：
    
    java rmi.NetS 2000 （端口号为2000）
    
    键入如下命令启动客户端：
    
    java rmi.NetC localhost 2000 localhost  2000
    
    可以单机模拟多台电脑的环境。当然也可以在Eclipse中配置运行时程序的参数来运行程序（运行时参数主要负责传递端口号，服务器地址等）。


Ch12：基于Agent实现的分布式计算 
    本程序的运行基于Aglet，首先要安装和配置Aglet：推荐安装Aglet稳定版本。对于不是开发者，我们推荐从编译好的包中安装。所有的库文件和平台都被打包成一个jar（Java Archive）文件，文件名称表明文件的版本号。比如本章采用的是Aglet的2.0.2版本，那么它的jar文件名称就是aglets-2.0.2.jar（ch12文件夹下有改归档文件）。下面详细说明了如何从该jar文件中安装Aglet平台。

   （1） 解压jar文件
    既然Aglet以jar文件的格式发布，那么我们首先要解压它。可以直接右键解压，也可以用如下命令如下解压归档文件：

    jar xvf aglets-2.0.2.jar

    一旦解压成功，就可以看到一些如下所示的子目录：

    bin——bin目录包含了Aglet平台所有的可执行程序，比如掌控接收Agent的后台（daemon）程序，另外bin目录还包含了进一步安装需要的文件。
    cnf——cnf目录包含了Aglet平台的配置文件。
    public——public目录包含了Agent的一些例子，而且还应该包含我们自己编写的Agent的根目录。
    lib——lib目录包含了Aglet的库文件和其他Aglet技术所需要的库文件。


    （2）Aglet的安装
    为了安装Aglet平台需要使用前面提到的Apache Ant工具。首先进入bin目录，在bin目录下有一个build.xml文件，然后执行如下命令：
    
    ant
  
    在aglets平台安装时，我们可以看到当前Aglet版本的信息。像其他的Java应用程序一样，Aglet平台需要通过Java policy文件（通常是.java.policy）来打开Socket、执行Agent、获取本地文件等。这项工作可以通过ant来完成，我们可以在bin目录下输入如下指令：

    ant install-home


   （3）配置环境变量
    为了运行Aglet平台，需要设置如下环境变量：AGLETS_HOME和AGLETS_PATH。另外，为了更方便地运行Aglet，可以将Aglet的bin目录加入到所运行的计算机的PATH变量中。当Aglet运行于Microsoft Windows系统时，假设Aglet安装在如下目录：
  
    c:\java\aglets
  
   可以进行如下设置：

    set AGLETS_HOME=c:\java\aglets
    set AGLETS_PATH=%AGLETS_HOME%
    set PATH=%PATH%;\%AGLETS_HOME%\bin

    当然，我们也可以在控制面板中配置环境变量。


   （4）Aglet的启动
    一旦已经安装配置好Aglet平台和policy文件，就可以运行默认Aglet服务（Tahiti服务），只需在bin目录下执行如下命令：

    agletsd
   
    Tahiti会要求用户鉴别确认用户名和密码，默认的用户名是anonymous，默认的密码是aglets。具体程序执行过程可参见本书第12.5.3计算过程一节。



    非常感谢对我们图书的支持：读者服务邮箱booksaga@126.com，投稿热线010-82728184-802
    *******欢迎登录我们的网站了解更多的图书信息，www.booksaga.com
    ----------图格新知将与您携手共进
