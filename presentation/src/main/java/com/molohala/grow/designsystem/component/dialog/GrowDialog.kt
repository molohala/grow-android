package com.molohala.grow.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.molohala.grow.designsystem.component.button.ButtonType
import com.molohala.grow.designsystem.component.button.GrowCTAButton
import com.molohala.grow.designsystem.component.button.GrowTextButton
import com.molohala.grow.designsystem.foundation.GrowTheme
import com.molohala.grow.designsystem.foundation.shadow.ShadowType
import com.molohala.grow.designsystem.foundation.shadow.growShadow

@Composable
fun GrowDialog(
    title: String,
    content: String? = null,
    dismissText: String = "닫기",
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .growShadow(ShadowType.ElevationBlack2)
                .background(
                    color = GrowTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        color = GrowTheme.colorScheme.textNormal,
                        style = GrowTheme.typography.headline1B,
                    )
                    content?.let {
                        Text(
                            text = it,
                            color = GrowTheme.colorScheme.textAlt,
                            style = GrowTheme.typography.bodyMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    GrowTextButton(
                        text = dismissText,
                        type = ButtonType.Medium,
                        onClick = onDismissRequest
                    )
                }
            }
        }
    }
}

@Composable
fun GrowDialog(
    title: String,
    content: String? = null,
    cancelText: String = "닫기",
    successText: String = "확인",
    onSuccessRequest: () -> Unit,
    onCancelRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .growShadow(ShadowType.ElevationBlack2)
                .background(
                    color = GrowTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp),
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        color = GrowTheme.colorScheme.textNormal,
                        style = GrowTheme.typography.headline1B,
                    )
                    content?.let {
                        Text(
                            text = it,
                            color = GrowTheme.colorScheme.textAlt,
                            style = GrowTheme.typography.bodyMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GrowTextButton(
                        modifier = Modifier
                            .weight(1f),
                        text = cancelText,
                        type = ButtonType.Medium,
                        onClick = onCancelRequest
                    )
                    GrowCTAButton(
                        modifier = Modifier
                            .weight(1f),
                        text = successText,
                        onClick = onSuccessRequest
                    )
                }
            }
        }
    }
}