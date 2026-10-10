package com.example.liquidglass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.catalog.components.LiquidBottomTabs
import com.kyant.backdrop.catalog.components.LocalLiquidBottomTabScale
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.vibrancy
import com.kyant.shapes.Capsule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                LiquidGlassDemo()
            }
        }
    }
}

@Composable
fun LiquidGlassDemo() {
    var selectedTab by remember { mutableIntStateOf(2) }

    // 背景采样源：所有需要“玻璃”效果的内容都绘制到它上面
    val backdrop = rememberLayerBackdrop()

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF6A5ACD),
                        Color(0xFF2E86DE),
                        Color(0xFF00B894)
                    )
                )
            )
            // 把背景绘制进 backdrop（layerBackdrop 采样它下方内容）
            .drawBackdrop(
                backdrop = backdrop,
                shape = { Capsule() },
                effects = {},
                layerBlock = {
                    // 采样整块屏幕
                }
            )
    ) {
        // ---------- 页面内容 ----------
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "已选择第 ${selectedTab + 1} 个标签",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "拖动底栏 / 点击图标试试液态玻璃效果",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }

        // ---------- 悬浮液态玻璃底栏 ----------
        LiquidBottomTabs(
            selectedTabIndex = { selectedTab },
            onTabSelected = { selectedTab = it },
            backdrop = backdrop,
            tabsCount = 4,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            LiquidTabItem(0, Icons.Filled.Home, "首页")
            LiquidTabItem(1, Icons.Filled.Search, "搜索")
            LiquidTabItem(2, Icons.Filled.Favorite, "收藏")
            LiquidTabItem(3, Icons.Filled.Person, "我的")
        }
    }
}

@Composable
private fun RowScope.LiquidTabItem(
    index: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    // 通过 LocalLiquidBottomTabScale 实现图标随按压放大
    val scale = LocalLiquidBottomTabScale.current

    Box(
        Modifier
            .weight(1f)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier
                .size(26.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}