# AGENTS.md

本文档为 AI 智能体提供本 Jenkins 插件项目的开发指导规范。

## 项目概述

这是一个用于企业微信（WeChat Work）通知的 Jenkins 插件。它提供了一个 Pipeline 步骤 `wxwork()`，用于向企业微信群机器人发送消息。

- **开发语言**: Java 17/21
- **构建工具**: Maven
- **框架**: Jenkins 插件（Jelly 视图、Stapler 数据绑定）
- **主要依赖**: Jackson, Lombok, Jenkins Workflow API

## 项目结构

```
wxwork-notification-plugin/
├── src/
│   ├── main/
│   │   ├── java/io/jenkins/plugins/wxwork/
│   │   │   ├── bo/               # 业务对象（消息、流水线变量）
│   │   │   │   ├── message/      # 消息类型（Text、Markdown、Image）
│   │   │   │   ├── RobotPipelineVars.java
│   │   │   │   └── RunUser.java
│   │   │   ├── contract/         # 接口和抽象类
│   │   │   │   ├── HttpClient.java
│   │   │   │   ├── RobotRequest.java
│   │   │   │   └── ...
│   │   │   ├── enums/            # 枚举类型
│   │   │   │   ├── HttpMethod.java
│   │   │   │   └── MessageType.java
│   │   │   ├── factory/          # 工厂类
│   │   │   ├── httpclient/       # HTTP 客户端实现
│   │   │   ├── protocol/         # 协议实现（企业微信）
│   │   │   ├── robot/            # 机器人消息发送实现
│   │   │   ├── transfer/         # 消息转换逻辑
│   │   │   ├── utils/            # 工具类
│   │   │   ├── WXWorkStep.java   # Pipeline 步骤主类
│   │   │   └── WXWorkGlobalConfig.java  # 全局配置
│   │   └── resources/
│   │       └── io/jenkins/plugins/wxwork/
│   │           ├── Messages.properties    # 国际化资源
│   │           ├── *.jelly                # Jelly 视图文件
│   │           └── config.properties      # 配置属性
│   └── test/
│       └── java/                 # 测试代码（目前为空）
├── pom.xml                       # Maven 配置
└── README.md                     # 使用文档
```

## 构建命令

```bash
# 完整构建（包含测试和 SpotBugs 检查）
mvn clean install

# 仅构建（跳过测试）
mvn clean install -DskipTests

# 运行所有测试
mvn test

# 运行单个测试类
mvn test -Dtest=InjectedTest

# 运行单个测试方法
mvn test -Dtest=ClassName#methodName

# 运行 SpotBugs 静态分析
mvn spotbugs:check

# 启动 SpotBugs GUI 查看报告
mvn spotbugs:gui

# 清理构建产物
mvn clean

# 打包插件（生成 .hpi 文件）
mvn package

# 启动 Jenkins 并加载插件（用于本地测试）
mvn hpi:run
```

## 代码风格规范

### 导入规范

- 使用通配符导入同一包内的静态工具类
- 按以下顺序分组导入：
  1. `java.*` 和 `javax.*`
  2. 第三方库
  3. 项目内部包
- 使用 Lombok 注解减少样板代码

**示例:**
```java
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import java.util.*;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
```

### 代码格式

- **缩进**: 4 个空格（不使用 Tab）
- **行宽**: 遵循现有代码风格（通常 120 字符）
- **大括号**: 类和方法声明的大括号与声明在同一行
- **空行**: 方法和类声明之间保留空行

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | PascalCase | `WXWorkRobotMessageSender` |
| 接口名 | PascalCase | `RobotRequest` |
| 方法名 | camelCase | `sendMessage()` |
| 常量名 | UPPER_SNAKE_CASE | `IGNORE_SSL_DOMAIN` |
| 变量名 | camelCase | `robotPropertyList` |
| 布尔字段 | 语义前缀 | `atMe`, `atAll` |

### 类型与注解

- 类型明确时使用 `var` 声明局部变量
- 使用 Lombok 注解：`@Getter`、`@Setter`、`@Builder`
- 非空参数使用 `@NonNull`（来自 `edu.umd.cs.findbugs.annotations`）
- Jenkins Stapler 数据绑定使用 `@DataBoundConstructor` 和 `@DataBoundSetter`
- 扩展点使用 `@Extension` 标注
- Jenkins 注入字段使用 `@SuppressWarnings("unused")`

### 注释规范

- 类和公共方法必须使用 Javadoc
- 简短描述格式：`/** <p>描述内容</p> */`
- 类 Javadoc 包含作者和日期：`@author nekoimi 2022/07/15`
- 内部文档使用中文注释（项目现有约定）
- 避免对自解释代码添加冗余注释

**示例:**
```java
/**
 * <p>企业微信群机器人消息发送器</p>
 *
 * @author nekoimi 2022/07/15
 */
public class WXWorkRobotMessageSender {
    /**
     * <p>发送消息到企业微信群机器人</p>
     *
     * @param property 机器人配置属性
     * @param request 消息请求对象
     * @return 发送结果响应
     */
    public RobotResponse send(RobotProperty property, RobotRequest request) {
        // 实现逻辑
    }
}
```

### 错误处理

- 对 `AutoCloseable` 资源使用 try-with-resources
- 通过 Jenkins `TaskListener` 记录错误日志
- Pipeline 步骤中使用 `listener.error()` 显示用户可见错误
- 非关键路径返回 null 或默认值，而非抛出异常

**示例:**
```java
try {
    // 执行操作
} catch (Exception e) {
    defaultHttpResponse.setStatusCode(500);
    defaultHttpResponse.setErrorMessage(e.getMessage());
}
```

## 架构模式

### 设计模式

1. **单例模式**: 使用静态内部类实现线程安全的单例
   ```java
   public class WXWorkRobotMessageSender {
       public static RobotMessageSender instance() {
           return SingletonHolder.robotMessageSender;
       }
       
       private static class SingletonHolder {
           private static final RobotMessageSender robotMessageSender = 
               new WXWorkRobotMessageSender();
       }
   }
   ```

2. **工厂模式**: 使用工厂类创建复杂对象
   ```java
   public class RobotMessageFactory {
       public static RobotRequest makeRobotRequest(RobotPipelineVars vars) {
           // 根据消息类型创建对应的消息对象
       }
   }
   ```

3. **策略模式**: 使用 Transfer 接口处理不同消息类型
   ```java
   public interface RobotMessageTransfer {
       boolean supports(MessageType messageType);
       RobotRequest transferRobotRequest(RobotPipelineVars pipelineVars);
   }
   ```

4. **建造者模式**: 使用 Lombok `@Builder` 构建复杂对象
   ```java
   @Builder
   public class RobotPipelineVars {
       private Run<?, ?> run;
       private EnvVars envVars;
       // ...
   }
   ```

### 包职责

- `bo`: 业务对象，封装消息和流水线变量
- `contract`: 定义接口和抽象，实现依赖倒置
- `enums`: 枚举类型定义
- `factory`: 对象创建工厂
- `httpclient`: HTTP 客户端实现（支持 HTTPS）
- `protocol`: 企业微信协议的具体实现
- `robot`: 机器人消息发送业务逻辑
- `transfer`: 消息类型转换器
- `utils`: 工具类（JSON 处理、Jenkins 工具）

## Jenkins 开发规范

### Pipeline 步骤开发

1. 继承 `Step` 类
2. 必需参数使用 `@DataBoundConstructor`
3. 可选参数使用 `@DataBoundSetter`
4. 实现 `StepExecution` 进行异步执行
5. 从 `getDescriptor()` 返回 `StepDescriptor` 实现

**示例:**
```java
@Getter
@Setter
public class WXWorkStep extends Step {
    private String robot;
    private MessageType type = MessageType.TEXT;
    
    @DataBoundConstructor
    public WXWorkStep(String robot) {
        this.robot = robot;
    }
    
    @DataBoundSetter
    public void setType(String type) {
        this.type = MessageType.typeValueOf(type);
    }
    
    @Override
    public StepExecution start(StepContext context) {
        return new WXWorkStepExecution(this, context);
    }
}
```

### 全局配置

1. 继承 `GlobalConfiguration`
2. 使用 `@Extension(ordinal = N)` 指定加载顺序
3. 通过 `GlobalConfiguration.all().getInstance()` 获取单例

### Jelly 视图

- 位置: `src/main/resources/io/jenkins/plugins/wxwork/`
- 表单配置: `config.jelly`
- 插件描述: `index.jelly`
- 国际化: `.properties` 文件

## 重要提示

1. **Lombok**: 本项目大量使用 Lombok，确保 IDE 开启注解处理功能
2. **SpotBugs**: 已启用，提交前必须解决所有警告
3. **Jenkins 基线**: 2.479.3（定义于 pom.xml）
4. **空值检查**: 使用 `Objects.isNull()` 和 `Objects.nonNull()`
5. **中文注释**: 遵循项目现有约定，内部文档使用中文
6. **插件产物**: `wxwork-notification.hpi`

## 常见问题

**Q: 如何添加新的消息类型？**  
A: 1) 在 `MessageType` 枚举中添加新类型；2) 在 `bo/message/` 创建消息类；3) 实现 `RobotMessageTransfer` 接口；4) 在 `RobotMessageFactory` 中注册。

**Q: 如何调试插件？**  
A: 运行 `mvn hpi:run` 启动 Jenkins，插件会自动加载，可在浏览器访问 `http://localhost:8080/jenkins` 测试。

**Q: SpotBugs 报错如何处理？**  
A: 运行 `mvn spotbugs:gui` 查看详细报告，根据提示修复潜在问题，必要时可添加 `@SuppressFBWarnings` 注解并说明原因。
