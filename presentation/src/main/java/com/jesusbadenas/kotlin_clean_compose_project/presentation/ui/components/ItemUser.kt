package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R

@Preview
@Composable
private fun PreviewItemUser() {
    ItemUser(
        user = User(
            id = 1,
            name = "Jesús Badenas",
            website = "https://jesusbadenas.com",
            imageUrl = "https://thispersondoesnotexist.com/"
        )
    )
}

@Composable
fun ItemUser(
    user: User,
    onNavigateToUserDetail: ((userId: Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = stringResource(R.string.user_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(54.dp),
                painter = rememberAsyncImagePainter(user.imageUrl)
            )
            Column(
                modifier = Modifier.wrapContentSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp)
                        .wrapContentSize(),
                    style = MaterialTheme.typography.titleMedium,
                    text = user.name.orEmpty()
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    style = MaterialTheme.typography.bodySmall,
                    text = user.website.orEmpty()
                )
            }
            Column(
                modifier = Modifier.wrapContentSize()
            ) {
                Button(
                    modifier = Modifier.wrapContentSize(),
                    onClick = {
                        onNavigateToUserDetail?.invoke(user.id)
                    }
                ) {
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = stringResource(R.string.btn_text_view_detail)
                    )
                }
            }
        }
    }
}
