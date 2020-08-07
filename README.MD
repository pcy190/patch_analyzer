# patch analyzer

分析apk间潜在安全补丁的自动化工具。

## Introduction

通过分析APK间的差异补丁，找到潜在的安全补丁位置，以期分析历史安全问题。


为了防止proguard等低强度混淆工具对我们静态分析的干扰，通过`javaparser`库分析java code的AST结构，并对两个版本的函数体进行相似度匹配，找到不同版本间的函数映射关系，以防止函数名对diff的干扰。

而后我们对smali diff追踪出来的函数进行过程内分析。分析的主要目标分为两类：
- 被新增的条件判断所控制的相似基本块
- 被潜在安全问题的条件判断所控制的相似基本块


在对源代码进行相似性评估时，先使用了`java-diff-utils`库对潜在的差异代码追踪位置。而后再通过一系列模式匹配，去除变量名混淆带来的干扰问题。而后，我们获取目标函数的函数体，再进行基本块进行查找识别工作。

为了达到低误报检测安全补丁的目的，提前收集了有关系统组件APP的不同版本，并进行了对应测试。对部分的补丁结果分析后，通过调整识别模式来优化误报的问题。

## Tool Design


- smali diff:
    - 通过baksmali生成smali，并通过相似度识别比较分析出similar的目标函数以及其文件位置,生成similar.json分析文件。

- decompiler
    - 通过jeb的接口将apk反编译到java工程。

- ast parser:
    - 解析不同版本间相似函数的映射关系，以抗混淆
    - 解析函数体的指令内容
    
- source code valuation:
    - 解析条件判断指令的内容，并进行相似度比较
    - 分析指令的相似度，查找相似的基本块
    - 匹配安全补丁的模式


## Structure
```
.
├── build.gradle (generate runnable patch_analyzer.jar)
├── cases ( example java code of special cases )
└── src
        source directory of patch_analyzer
```

## Requirement

- jeb3 pro or higher (pro version)
- Java 1.8 or higher
- Gradle 4.4.2 or higher
- smali diff tools 

## Usage
build the project with
```
gradle build
```

## run

把两个版本的apk以及分析后的`similar.json`文件放入同一个文件夹。
而后运行
```
java -jar patch_analyser.jar commonPath sourcePkg targetPkg jebPath
```
- commonPath为存放两个apk的上级目录
- sourcePkg为高版本apk的包名
- targetPkg为低版本apk的包名
- jebPath为jeb的根路径

例如jeb路径在`/home/jeb-pro-3.19`下，且在文件夹`/home/com.xiaomi.misettings`下有如下文件
```
.
├── com.xiaomi.misettings-2.7.2-200317011-2dad34ab84d36a3a.apk
├── com.xiaomi.misettings-2.8.5-200430011-24c9c68e67f1bfc4.apk
└── similar.json
```
则运行
```
 java -jar patch_analyser.jar /home/com.xiaomi.misettings  com.xiaomi.misettings-2.8.5-200430011-24c9c68e67f1bfc4 com.xiaomi.misettings-2.7.2-200317011-2dad34ab84d36a3a /home/jeb-pro-3.19
```

similar.json的文件格式
```
{
	"1": {
		"filename": "...hardware/CitFpFodSensorCheckActivity.smali ...hardware/CitFpFodSensorCheckActivity.smali",
		"methods": "{\"onActivityResult(IILandroid/content/Intent;)V\": \"onActivityResult(IILandroid/content/Intent;)V\"}"
	},
	"2": {
		"filename": "...sensor/CitGpsCheckActivity.smali ...sensor/CitGpsCheckActivity.smali",
		"methods": "{\"<init>()V\": \"<init>()V\", \"onPause()V\": \"onPause()V\", \"onResume()V\": \"onResume()V\"}"
	},
	"3": {
		"filename": "...utils/FileUtil.smali ...utils/FileUtil.smali",
		"methods": "{\"copyFile(Ljava/lang/String;Ljava/lang/String;)I\": \"copyFile(Ljava/lang/String;Ljava/lang/String;)I\"}"
	}
}
```
序号表示不同的文件，filename表示两个版本的smali文件名，methods表示两个中存在差异的方法签名。其中，不同版本间用空格分割。


## analysis
分析结果中，例如
```
Similar Condition Found in the MenuPopupWindow.java
[position: 1, size: 9, lines: [    if (Build.VERSION.SDK_INT <= 28) {,         Method v0 = MenuPopupWindow.K;,         if (v0 != null) {,             try {,                 v0.invoke(this.J, Boolean.valueOf(arg5));,             } catch (Exception unused_ex) {,                 Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");,             },             return;]]
[position: 1, size: 6, lines: [    Method v0 = MenuPopupWindow.K;,     if (v0 != null) {,         try {,             v0.invoke(this.J, Boolean.valueOf(arg5));,         } catch (Exception unused_ex) {,             Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");]]
CHANGE
1
1
>>>>>>>>>>>>>>>>>>>>>>>>>>>
Optional[{
    if (Build.VERSION.SDK_INT <= 28) {
        Method v0 = MenuPopupWindow.K;
        if (v0 != null) {
            try {
                v0.invoke(this.J, Boolean.valueOf(arg5));
            } catch (Exception unused_ex) {
                Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
            }
            return;
        }
    } else {
        this.J.setTouchModal(arg5);
    }
}]
<<<<<<<<<<<<<<<<<<<<<<<<<<<
Optional[{
    Method v0 = MenuPopupWindow.K;
    if (v0 != null) {
        try {
            v0.invoke(this.J, Boolean.valueOf(arg5));
        } catch (Exception unused_ex) {
            Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
        }
        return;
    }
}]
```
在第二行的position和size表示source apk中对应的位置，此处为`MenuPopupWindow.java`。同理，第三行的position和size表示target apk中对应的位置。

而后在`>>>>>>>>`和`<<<<<<<`之间的为source对应的上下文dump信息，默认打印范围为会在发生安全补丁的前4行至结束后的6行。

在`<<<<<<<`之后的为target的上下文dump信息。

此处的补丁为添加了对`Build.VERSION.SDK_INT`的判断。