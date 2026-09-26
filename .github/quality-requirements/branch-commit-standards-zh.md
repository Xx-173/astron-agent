# 分支与提交规范

本文档定义了项目的分支管理和提交消息规范，确保团队协作的一致性和代码质量。

## 分支管理规范

### 分支类型

| 分支类型 | 命名格式 | 用途 | 示例 |
|---------|---------|------|------|
| **主分支** | `main` | 生产环境代码 | `main` |
| **开发分支** | `develop` | 开发集成分支 | `develop` |
| **功能分支** | `feature/功能名` | 新功能开发 | `feature/user-login` |
| **修复分支** | `bugfix/问题名` | Bug修复 | `bugfix/auth-error` |
| **热修复分支** | `hotfix/补丁名` | 紧急修复 | `hotfix/security-patch` |
| **设计分支** | `design/设计名` | UI/UX优化 | `design/mobile-layout` |
| **重构分支** | `refactor/重构名` | 代码重构 | `refactor/user-service` |
| **测试分支** | `test/测试名` | 测试开发 | `test/integration-tests` |
| **文档分支** | `doc/文档名` | 文档更新 | `doc/api-guide` |

### 分支创建命令

```bash
# 使用Makefile命令创建规范分支
make new-feature name=user-login      # 创建功能分支
make new-bugfix name=auth-error       # 创建修复分支
make new-hotfix name=security-patch   # 创建热修复分支
make new-design name=mobile-layout    # 创建设计分支

# 手动创建分支
git checkout -b feature/user-login
git checkout -b bugfix/auth-error
git checkout -b hotfix/security-patch
```

### 分支工作流

```bash
# 1. 从main分支创建功能分支
git checkout main
git pull origin main
git checkout -b feature/user-login

# 2. 开发完成后合并到develop
git checkout develop
git merge feature/user-login
git push origin develop

# 3. 通过Pull Request合并到main
# 在GitHub上创建PR: develop → main
```

## 提交消息规范

### 提交类型

| 类型 | 说明 | 示例 |
|------|------|------|
| `feat` | 新功能 | `feat: 支持手机号登录` |
| `fix` | Bug修复 | `fix: 解决token过期问题` |
| `docs` | 文档更新 | `docs: 完善API说明` |
| `style` | 代码格式 | `style: 统一缩进格式` |
| `refactor` | 代码重构 | `refactor: 拆分用户服务` |
| `perf` | 性能优化 | `perf: 优化数据库查询` |
| `test` | 测试相关 | `test: 添加单元测试` |
| `build` | 构建系统 | `build: 升级webpack到5.0` |
| `ci` | CI/CD配置 | `ci: 添加GitHub Actions` |
| `chore` | 杂项任务 | `chore: 更新.gitignore` |
| `revert` | 回滚提交 | `revert: 回滚commit abc123` |

### 提交格式

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### 格式要求

- **类型**: 必须使用上述预定义类型
- **范围**: 可选，表示影响范围（如模块名）
- **描述**: 简洁明了，使用中文
- **长度**: 标题不超过50字符，正文每行不超过72字符
- **时态**: 使用现在时，如"添加"而不是"添加了"

### 提交示例

```bash
# 基础格式
feat: 添加用户登录功能
fix: 修复密码验证bug
docs: 更新API文档

# 带范围的格式
feat(auth): 添加OAuth2登录支持
fix(api): 修复用户信息查询接口
docs(guide): 完善快速开始指南

# 详细格式
feat: 添加用户权限管理

- 实现角色基础权限控制
- 添加权限验证中间件
- 更新用户管理界面

Closes #123
```

## 质量门禁

### 提交前检查

```bash
# 自动运行（通过Git hooks）
make format    # 代码格式化
make check     # 质量检查
make test      # 运行测试

# 手动检查
make check-branch    # 检查分支命名
make safe-push       # 安全推送
```

### 检查项目

- **代码格式**: 自动格式化所有语言代码
- **语法检查**: 通过各语言的lint工具
- **类型检查**: TypeScript/Python类型验证
- **复杂度控制**: 函数复杂度限制
- **分支命名**: 验证分支命名规范
- **提交消息**: 验证提交消息格式

## 最佳实践

### 开发流程

1. **开始开发**: `make dev-setup` (首次) → `make new-feature name=功能名`
2. **编写代码**: 频繁commit，使用规范的commit message
3. **提交前检查**: `make fmt && make check` 确保质量
4. **推送代码**: `make safe-push` 验证并推送
5. **创建PR**: 通过GitHub界面创建Pull Request
6. **代码审查**: 团队review，修改建议
7. **合并代码**: 审查通过后合并到主分支

### 团队约定

- 🚫 **禁止直接推送到main/develop分支**
- ✅ **必须通过分支开发 + PR流程**
- ✅ **提交前必须通过所有质量检查**
- ✅ **使用规范的分支命名和提交消息**
- ✅ **大功能拆分为小commit，便于review**

## 常见问题

### 分支管理问题

**问题**: 在错误分支开发
**解决**: 使用git命令迁移代码到正确分支
```bash
git stash
git checkout -b feature/correct-branch
git stash pop
```

**问题**: 分支名不规范
**解决**: 重命名分支或创建新的规范分支
```bash
git branch -m old-branch-name feature/new-name
```

### 提交问题

**问题**: 提交消息格式错误
**解决**: 使用 `git commit --amend` 修改最近一次提交
```bash
git commit --amend -m "feat: 正确的提交消息"
```

**问题**: 质量检查失败
**解决**: 运行 `make check` 查看详细错误，修复后重新提交

## 相关文档

- [代码质量要求](./code-requirements-zh.md) - 各语言代码质量检测
- [Makefile使用指南](../../docs/zh/Makefile-readme.md) - 完整的Makefile命令说明
- [本地开发配置](../../docs/zh/Makefile-readme.md#本地开发配置) - 使用`.localci.toml`进行模块化开发
