# patch analyzer

自动化分析识别apk间潜在的security patch

## Introduction

通过分析APK间的差异补丁，找到潜在的安全补丁位置，以期分析历史安全问题。



## Tool Design


- smali diff:
    - 通过baksmali生成smali，并通过相似度识别比较分析出similar的目标函数以及其文件位置。
    
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
└── src
        source directory of apkRepair
```

## Requirement

- jeb3 pro or higher
- Java 1.8 or higher
- Gradle 4.4.2 or higher
- smali diff tools (PENGDING)

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