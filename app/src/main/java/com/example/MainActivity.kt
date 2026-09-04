package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MinimalActionDark
import com.example.ui.theme.MinimalBg
import com.example.ui.theme.MinimalBorderFenix
import com.example.ui.theme.MinimalBorderJotur
import com.example.ui.theme.MinimalDivider
import com.example.ui.theme.MinimalOnPrimaryContainer
import com.example.ui.theme.MinimalOnSecondaryContainer
import com.example.ui.theme.MinimalPrimary
import com.example.ui.theme.MinimalPrimaryContainer
import com.example.ui.theme.MinimalSecondaryContainer
import com.example.ui.theme.MinimalTextMain
import com.example.ui.theme.MinimalTextMuted
import com.example.ui.theme.MinimalTextSubtle
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          contentWindowInsets = WindowInsets.safeDrawing,
        ) { innerPadding ->
          HorariosOnibusScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

private const val URL_JOTUR = "https://www.jotur.com.br/horarios/"
private const val URL_FENIX = "https://www.consorciofenix.com.br/horarios"

@Composable
fun HorariosOnibusScreen(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  Box(
    modifier =
      modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      modifier =
        Modifier
          .widthIn(max = 448.dp)
          .fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(horizontal = 24.dp, vertical = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      // Header Bus Icon Badge (Clean Minimalism rounded-[28px], deep purple, shadow)
      Surface(
        shape = RoundedCornerShape(28.dp),
        color = MinimalPrimary,
        shadowElevation = 8.dp,
        modifier = Modifier.size(80.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Filled.DirectionsBus,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(48.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // Title
      Text(
        text = stringResource(R.string.bus_schedules_title),
        style =
          MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            letterSpacing = (-0.5).sp,
            color = MinimalTextMain,
          ),
        textAlign = TextAlign.Center,
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Subtitle
      Text(
        text = stringResource(R.string.bus_schedules_subtitle),
        style =
          MaterialTheme.typography.bodyLarge.copy(
            color = MinimalTextMuted,
            fontSize = 16.sp,
            lineHeight = 24.sp,
          ),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 16.dp),
      )

      Spacer(modifier = Modifier.height(32.dp))

      // Button 1: Horários Jotur (Clean Minimalism soft lavender container with deep purple accents)
      MinimalTransitButton(
        category = stringResource(R.string.category_jotur),
        title = stringResource(R.string.btn_jotur),
        containerColor = MinimalPrimaryContainer,
        borderColor = MinimalBorderJotur.copy(alpha = 0.5f),
        textColor = MinimalOnPrimaryContainer,
        actionBadgeColor = MinimalOnPrimaryContainer,
        testTag = "button_jotur",
        onClick = { openWebPage(context, URL_JOTUR) },
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Button 2: Horários Fênix (Clean Minimalism warm neutral-lavender container with slate accents)
      MinimalTransitButton(
        category = stringResource(R.string.category_fenix),
        title = stringResource(R.string.btn_fenix),
        containerColor = MinimalSecondaryContainer,
        borderColor = MinimalBorderFenix,
        textColor = MinimalOnSecondaryContainer,
        actionBadgeColor = MinimalActionDark,
        testTag = "button_fenix",
        onClick = { openWebPage(context, URL_FENIX) },
      )

      Spacer(modifier = Modifier.height(36.dp))

      // Footer divider with subtle region label
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
      ) {
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          thickness = 1.dp,
          color = MinimalDivider,
        )
        Text(
          text = stringResource(R.string.region_label).uppercase(),
          style =
            MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              letterSpacing = 2.sp,
              color = MinimalTextSubtle,
            ),
          modifier = Modifier.padding(horizontal = 12.dp),
        )
        HorizontalDivider(
          modifier = Modifier.weight(1f),
          thickness = 1.dp,
          color = MinimalDivider,
        )
      }
    }
  }
}

@Composable
fun MinimalTransitButton(
  category: String,
  title: String,
  containerColor: Color,
  borderColor: Color,
  textColor: Color,
  actionBadgeColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors =
      CardDefaults.cardColors(
        containerColor = containerColor,
      ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp, pressedElevation = 0.dp),
    border = BorderStroke(1.dp, borderColor),
    modifier =
      modifier
        .fillMaxWidth()
        .heightIn(min = 84.dp)
        .clip(RoundedCornerShape(24.dp))
        .clickable(onClick = onClick)
        .testTag(testTag),
  ) {
    Row(
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp, vertical = 20.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = category.uppercase(),
          style =
            MaterialTheme.typography.labelSmall.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.2.sp,
              color = textColor.copy(alpha = 0.6f),
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = title,
          style =
            MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.SemiBold,
              fontSize = 20.sp,
              color = textColor,
            ),
        )
      }

      // Action icon badge with rounded-2xl
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = actionBadgeColor,
        modifier = Modifier.size(44.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
            contentDescription = "Abrir em nova aba",
            tint = Color.White,
            modifier = Modifier.size(20.dp),
          )
        }
      }
    }
  }
}

private fun openWebPage(context: Context, url: String) {
  try {
    val intent =
      Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      }
    context.startActivity(intent)
  } catch (e: Exception) {
    Toast.makeText(context, "Não foi possível abrir o navegador", Toast.LENGTH_SHORT).show()
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun HorariosOnibusPreview() {
  MyApplicationTheme { HorariosOnibusScreen() }
}

