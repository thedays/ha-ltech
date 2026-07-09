# Ltech Home Assistant Integration - Release Guide

## 发布流程

### 1. 打包插件

进入插件目录，将文件直接打包到 zip 根目录：

```bash
cd /path/to/ha-ltech/custom_components/ltech
zip ../../ltech-hass-integration.zip *
```

验证压缩包结构：
```bash
unzip -l /path/to/ha-ltech/ltech-hass-integration.zip
```

**正确的压缩包结构**：
```
ltech-hass-integration.zip
├── __init__.py
├── api.py
├── config_flow.py
├── const.py
├── coordinator.py
├── entity.py
├── light.py
├── manifest.json
├── README.md
├── sensor.py
└── switch.py
```

### 2. 提交代码

```bash
cd /path/to/ha-ltech

# 添加所有修改的文件
git add -A

# 提交代码
git commit -m "Update: Release v1.x.x"

# 推送代码到 GitHub
git push origin main
```

### 3. 创建 Tag

```bash
# 创建 tag（替换版本号）
git tag v1.0.0

# 推送 tag 到 GitHub
git push origin v1.0.0
```

**注意**：如果需要更新已存在的 tag：
```bash
git tag -d v1.0.0
git tag v1.0.0
git push origin v1.0.0 --force
```

### 4. 创建 GitHub Release

1. 访问 GitHub 仓库：https://github.com/thedays/ha-ltech/releases
2. 点击 **Draft a new release**
3. 在 **Choose a tag** 下拉菜单中选择刚刚创建的 tag（如 `v1.0.0`）
4. 填写 **Release title**（如 `v1.0.0 - Initial Release`）
5. 在 **Describe this release** 中添加发布说明
6. 点击 **Attach binaries by dropping them here or selecting them**
7. 选择 `ltech-hass-integration.zip` 文件上传
8. 点击 **Publish release**

## 版本号规则

使用语义化版本号（Semantic Versioning）：

- `v1.0.0` - 初始版本
- `v1.0.1` - Bug 修复
- `v1.1.0` - 新增功能
- `v2.0.0` - 重大变更（可能不兼容）

## 完整脚本示例

```bash
#!/bin/bash

# 版本号
VERSION="v1.0.0"

# 打包插件
cd /path/to/ha-ltech/custom_components/ltech
rm ../../ltech-hass-integration.zip
zip ../../ltech-hass-integration.zip *
echo "✅ 打包完成"

# 提交代码
cd /path/to/ha-ltech
git add -A
git commit -m "Update: Release ${VERSION}"
git push origin main
echo "✅ 代码推送完成"

# 创建并推送 tag
git tag -d ${VERSION} 2>/dev/null || true
git tag ${VERSION}
git push origin ${VERSION} --force
echo "✅ Tag ${VERSION} 创建完成"

echo ""
echo "请在 GitHub 上手动创建 Release:"
echo "https://github.com/thedays/ha-ltech/releases/new?tag=${VERSION}"
```

## 更新 Home Assistant 集成

用户更新步骤：

1. 删除旧的集成：**设置 > 设备与服务 > Ltech Smart Home > 删除**
2. 删除旧文件：`rm -rf config/custom_components/ltech/`
3. 下载最新的 `ltech-hass-integration.zip`
4. 解压到 `config/custom_components/ltech/`
5. 重启 Home Assistant
6. 重新添加集成