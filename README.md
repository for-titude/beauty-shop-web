

# 💅 客户美容店管理系统

> 一个基于 Spring Boot 的轻量级美容店业务管理 Web 应用，支持客户管理、美容项目维护、服务登记、月度统计等简单功能。




## 📌 功能特性

- ✅ **客户管理**：新增、查看客户（姓名、性别、电话）
- ✅ **美容项目管理**：维护项目名称与价格
- ✅ **美容登记**：记录客户消费（自动关联客户与项目）
- ✅ **统计报表**：
  - 指定月份各项目服务次数
  - 指定年份各客户到店次数
  - **指定月份总收入**（前端可视化）
- ✅ **数据安全**：
  - 客户性别限制为 `男`/`女`（数据库 CHECK 约束 + 前端下拉）
  - 数据库凭证通过**环境变量**注入（不硬编码）
- ✅ **响应式 UI**：基于 Tailwind CSS，美观简洁，适配桌面与平板

---

## 🛠️ 技术栈

| 类别   | 技术                             |
| ---- | ------------------------------ |
| 后端框架 | Spring Boot 3.4.0              |
| ORM  | MyBatis 3.0.3                  |
| 数据库  | MySQL 5.7                      |
| 前端模板 | Thymeleaf + Tailwind CSS (CDN) |
| 构建工具 | Maven                          |
| 开发语言 | Java 17                        |
| 存储过程 | MySQL Stored Procedure（用于统计）   |

---

## 🚀 快速启动（Windows）

### 前置要求

- ✅ **JDK 17**（如 [Eclipse Temurin 17](https://adoptium.net/)）
- ✅ **Maven 3.6+**
- ✅ **MySQL 5.7** 服务已启动
- ✅ 已创建数据库：`BeautyShopDB`

### 步骤

#### 1. 克隆或下载项目

```bash
git clone https://github.com/for-titude/beauty-shop-web.git
cd beauty-shop-web
```

#### 2. 初始化数据库

> 执行以下 SQL 脚本（含表结构 + 存储过程 + 测试数据）：

```sql
-- 文件：docs/init_db.sql（项目中提供）
-- 或在 MySQL 客户端中运行：
SOURCE path/to/your/project/docs/init_db.sql;
```

> 💡 该脚本包含：
> 
> - `Customer`, `BeautyService`, `Appointment` 表
> - 3 个统计用存储过程
> - 5 位客户 + 4 个项目 + 8 条记录（供演示）

#### 3. 设置数据库凭证（安全方式）

**不要修改 `application.yml`！** 使用 **PowerShell 临时环境变量**：

```powershell
# 打开 PowerShell（项目根目录）
$env:DB_USERNAME="root"
$env:DB_PASSWORD="你的MySQL密码"

# 启动应用
mvn spring-boot:run
```

> 🔒 密码仅存在于当前会话内存中，关闭窗口即销毁。

#### 4. 访问应用

打开浏览器访问：

```
http://localhost:8080
```

---

## 📂 项目结构

```
beauty-shop-web/
├── src/main/java
│   └── com/example/beautyshop
│       ├── controller/      # Web 控制器（首页、统计、表单）
│       ├── mapper/          # MyBatis Mapper 接口
│       ├── model/           # 实体类（Customer, BeautyService, Appointment）
│       └── BeautyShopApplication.java
├── src/main/resources
│   ├── mapper/     # MyBatis XML 映射文件
│   ├── templates/          # Thymeleaf 页面（含 Tailwind UI）
│   └── application.yml     # 配置文件（数据库 URL，凭证通过环境变量注入）
├── docs/
│   └── init_db.sql         # 数据库初始化脚本（建表+存储过程+测试数据）
├── .gitignore              # 忽略敏感文件（如 run.ps1）
├── pom.xml                 # Maven 依赖与插件
└── README.md
```

---

## 🔐 安全最佳实践

### 数据库凭证管理

| 环境       | 方式                                |
| -------- | --------------------------------- |
| **本地开发** | PowerShell 临时环境变量（推荐）             |
| **生产部署** | Docker `-e` 参数 / 服务器环境变量 / 密钥管理服务 |

> 🚫 **禁止** 将密码写入 `application.yml` 或提交到 Git！

### 示例：本地启动脚本（`run.ps1`）

```powershell
# run.ps1（放在项目根目录，**不要提交到 Git**）
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
mvn spring-boot:run
```

---

## 📊 存储过程说明

项目依赖以下 **MySQL 存储过程**（已包含在 `init_db.sql` 中）：

| 存储过程                                                | 功能          |
| --------------------------------------------------- | ----------- |
| `GetMonthlyIncome(IN year INT, IN month INT)`       | 返回指定年月总收入   |
| `GetMonthlyServiceCount(IN year INT, IN month INT)` | 返回各项目服务次数   |
| `GetYearlyCustomerCount(IN year INT)`               | 返回各客户年度到店次数 |

> 通过 JDBC `CallableStatement` 调用（见 `StatController.java`）

---

## 🧪 测试数据

启动后，首页将显示：

- **8 条美容记录**（2025-12-23 至 2025-12-29）
- **4 个美容项目**（价格 ¥128 ~ ¥598）
- **5 位客户**（3 女 2 男）

点击 **“本月统计”** 可查看 **12 月总收入：¥2514.00**

---

## 🐳 Docker 支持（可选）

> 未来可扩展：提供 `Dockerfile` 和 `docker-compose.yml`

当前可通过以下方式容器化运行：

```bash
docker run -e DB_USERNAME=root -e DB_PASSWORD=xxx -p 8080:8080 your-app
```

---

## 📝 开发者指南

### 如何新增功能？

1. **新增实体** → 创建 `model/xxx.java`
2. **新增表** → 更新 `init_db.sql`
3. **新增页面** → 添加 `templates/xxx.html`（使用 Tailwind）
4. **新增接口** → 编写 `controller` + `mapper`

### 常见问题

**Q：启动报 `com.mysql.cj.jdbc.Driver not assignable`？**  
A：确保 `pom.xml` 使用 `<artifactId>mysql-connector-j</artifactId>` + 版本 ≥ 8.4.0

**Q：Thymeleaf 页面不渲染？**  
A：检查 `templates/` 文件名是否与 `return "xxx";` 一致

---

## 📜 许可证

本项目仅供学习与教学使用。

---

## ❤️ 致谢

- Spring Boot 官方文档
- MyBatis 中文文档
- Tailwind CSS