# java-io
This is a long story.   
Linux - Kernel IO  -  
Java - IO -  

## 1.Index
1. Network Protocol  
2. Linux IO  
3. Java IO  

## 2.Linux IO

### 同步和异步
同步和异步与结果的`获取机制`有关。  
同步：调用发出后 -->  没有得到结果之前,就不返回;  
异步：调用发出后 -->  立即返回,不会立刻得到结果,在完成后，通过状态、通知和回调来通知调用者;  

### 阻塞与非阻塞
阻塞与非阻塞与等待结果通知时的`线程状态`有关。  
同步：调用发出后 -->  线程挂起,得到结果之后,线程结束;  
异步：调用发出后 -->  指在不能立刻得到结果之前，该函数不会阻塞当前线程;  

> 阻塞和同步是完全不同的概念。
> [阻塞和同步](https://blog.csdn.net/z_ryan/article/details/80873449)
> [阻塞和同步](https://www.jianshu.com/p/486b0965c296)
> [阻塞和同步](https://www.cnblogs.com/cyyz-le/p/10962818.html)
> [阻塞和同步](https://www.cnblogs.com/bakari/p/10966303.html)
> [操作系统IO](https://juejin.im/post/5eeadd81e51d4573c91b90b0?utm_source=gold_browser_extension)


## 3.Java IO

### 输入&输出
1. 标准输入 - 键盘 - System.in() - InputStream  
2. 标准输出 - 屏幕 - System.out() System.err() - PrintStream - OutputStream  

3. 格式化输入 - Scanner  
4. 格式化输出 - String.format()  

### Stream
流-->传输特定数据(字节||字符)的管道;  
流-->可以是文件,内存或是管道;  

#### InputStream

InputStream  
    |- FileInputStream(文件/字节)  
    |- ObjectInputStream(进程间传递/字节)  
    |- PipedInputStream(线程间传递/字节)                      |- DataInputStream(基本类型的流)  
    |- SequenceInputStream(多源顺序读取/字节)                 |- PushbackInputStream(读取部分元素-->逻辑判断-->压回去-->整体读)  
    |- FilterInputStream(优化性能读取)                   <---|- BufferedInputStream(加了缓存的InputStream)  
    |- StringBufferInputStream(读内存字符/字节)(@Deprecated) |- LineNumberInputStream(@Deprecated) (读取部分元素-->逻辑判断-->压回去-->整体读)  
    |- ByteArrayInputStream(读内存字节数组/字节)  



OutputStream  
    |- FileOutputStream(文件/字节)  
    |- ObjectOutputStream(进程间传递/字节)  
    |- PipedOutputStream(线程间传递/字节)               |- DataOutputStream  
    |- FilterOutputStream(优化性能读取)             <---|- BufferedOutputStream(加了缓存的InputStream)  
    |- ByteArrayOutputStream(读内存字节数组/字节)        |- PrintStream  


Reader  
    |- BufferedReader()          <---|- LineNumberReader()  
    |- CharArrayReader()  
    |- StringReader()          
    |- InputStreamReader()     <---|- FileReader()  
    |- PipedReader()       
    |- FilterReader()    <---|- PushbackReader()  
    
Writer  
    |- BufferedWriter()          <---|- LineNumberWriter()  
    |- CharArrayWriter()  
    |- StringWriter()          
    |- InputStreamWriter()     <---|- FileWriter()  
    |- PipedWriter()       
    |- FilterWriter()    <---|- PushbackWriter()  



FilterInputStream 提升性能，读数据时，增加了buffer,容量是512的倍数  



StringBufferInputStream(@Deprecated) --> 这个类不能正确地将字符转换成字节  
LineNumberInputStream(@Deprecated) --> 这个类错误地假设bytes能够足够表示characters  

[参考](https://www.cnblogs.com/lighten/p/7063161.html)
[参考](https://www.bilibili.com/video/BV14J41177bY?p=8)