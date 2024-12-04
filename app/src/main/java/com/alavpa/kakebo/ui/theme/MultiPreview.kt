import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "light mode",
    group = "ui mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "dark mode",
    group = "ui mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class MultiPreview