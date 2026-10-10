# 液态玻璃悬浮底栏 (Liquid Glass Bottom Bar)

基于 [Kyant0/AndroidLiquidGlass](https://github.com/Kyant0/AndroidLiquidGlass) 的 **Liquid Glass (Backdrop) 2.0.1** 库，实现 iOS 26 风格的 **悬浮液态玻璃底栏**，包含：

- ✅ 悬浮胶囊形玻璃底栏
- ✅ 拖动 / 点击切换标签
- ✅ **按压选择按钮**：选中指示块（跟手滑动、带果冻弹性、透镜折射、按压缩放）
- ✅ 按压高光（点哪亮哪）
- ✅ 图标随按压放大

## 📦 依赖版本

| 组件 | 版本 |
|---|---|
| Android Gradle Plugin | 8.7.3 |
| Kotlin | 2.1.20 |
| Gradle | 8.11.1 |
| Compose Multiplatform | 1.9.0 |
| backdrop (Kyant) | 2.0.1 |
| shapes (Kyant) | 1.2.1 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |

## 🚀 如何编译出 APK

### 方式一：Android Studio（最简单）

1. 用 **Android Studio（Ladybug 2024.2 或更新）** 打开本工程根目录
2. 等待 Gradle Sync 完成（会自动下载依赖）
3. 菜单 **Build → Build Bundle(s) / APK(s) → Build APK(s)**
4. 生成的 APK 在：`app/build/outputs/apk/debug/app-debug.apk`

### 方式二：命令行

```bash
# 在工程根目录
./gradlew assembleDebug

# 产物
# app/build/outputs/apk/debug/app-debug.apk
```

> 首次编译需要联网下载 Gradle 及依赖（约 300~600MB）。

## 📁 目录结构

```
LiquidGlassApp/
├── settings.gradle.kts              # 仓库配置
├── build.gradle.kts                 # 根构建脚本
├── gradle.properties
├── gradle/wrapper/
│   └── gradle-wrapper.properties
└── app/
    ├── build.gradle.kts             # 模块构建脚本（含 backdrop 依赖）
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/values/
        │   ├── strings.xml
        │   └── themes.xml
        └── java/
            ├── com/example/liquidglass/
            │   └── MainActivity.kt         # ⭐ 入口 + 底栏使用示例
            └── com/kyant/backdrop/catalog/
                ├── components/
                │   └── LiquidBottomTabs.kt  # ⭐ 底栏组件（含按压选择按钮）
                └── utils/
                    ├── DampedDragAnimation.kt   # 阻尼拖拽动画（果冻手感）
                    └── InteractiveHighlight.kt  # 按压高光
```

## 🔑 核心组件说明

### `LiquidBottomTabs`
```kotlin
LiquidBottomTabs(
    selectedTabIndex = { selectedTab },   // 当前选中的标签
    onTabSelected = { selectedTab = it }, // 切换回调
    backdrop = backdrop,                  // 背景采样源
    tabsCount = 4,                        // 标签数量
    modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 28.dp)
) {
    LiquidTabItem(0, Icons.Filled.Home, "首页")
    // ... 每个 tab 一个 item
}
```

三层结构：
1. **底层玻璃容器**：`Capsule` 胶囊 + `vibrancy/blur/lens`
2. **中层折射层**：跟随按压做透镜折射
3. **顶层指示器**：白色玻璃滑块 + 透镜 + 阴影 + 内阴影 + 弹性缩放

### 要点
- `backdrop` 必须由 `rememberLayerBackdrop()` 创建，并通过 `drawBackdrop` 把背景绘制进去，玻璃才能"看到"下方内容。
- 图标放大通过 `LocalLiquidBottomTabScale.current` 读取。

## ⚠️ 注意

- 液态玻璃的**折射/透镜**效果依赖 GPU 的 `RuntimeShader`（AGSL），Android 13+ 效果最佳，低版本会自动降级。
- 若 Gradle Sync 报版本冲突，可把 `compose` 相关依赖统一提到 `1.9.0`（或直接对齐 backdrop 的 1.12.0）。

## 📄 许可

代码来自 Kyant0/AndroidLiquidGlass（Apache-2.0）。本工程仅供学习参考。
