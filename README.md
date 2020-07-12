# java-io
## 输入&输出
1. 标准输入 - 键盘 - System.in() - InputStream
2. 标准输出 - 屏幕 - System.out() System.err() - PrintStream - OutputStream

3. 格式化输入 - Scanner
4. 格式化输出 - String.format()

## Stream
流-->传输特定数据(字节||字符)的管道;
流-->可以是文件,内存或是管道;

### InputStream

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