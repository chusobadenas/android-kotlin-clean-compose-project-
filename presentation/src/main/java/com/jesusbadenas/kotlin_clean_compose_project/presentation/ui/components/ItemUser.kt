package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components

import androidx.compose.foundation.Image
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.mock.mockUser

@Composable
fun ItemUser(
    user: User,
    onNavigateToUserDetail: ((userId: Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxSize()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (userImage, userNameText, userWebsiteText, viewDetailButton) = createRefs()
                createVerticalChain(userNameText, userWebsiteText)
                Image(
                    contentDescription = stringResource(R.string.user_image_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.constrainAs(userImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(64.dp),
                    painter = rememberAsyncImagePainter(user.imageUrl)
                )
                Text(
                    modifier = Modifier.constrainAs(userNameText) {
                            top.linkTo(parent.top)
                            start.linkTo(userImage.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .wrapContentSize(),
                    style = MaterialTheme.typography.titleMedium,
                    text = user.name.orEmpty()
                )
                Text(
                    modifier = Modifier.constrainAs(userWebsiteText) {
                            top.linkTo(parent.top)
                            start.linkTo(userImage.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .wrapContentSize(),
                    style = MaterialTheme.typography.bodySmall,
                    text = user.website.orEmpty()
                )
                Button(
                    modifier = Modifier.constrainAs(viewDetailButton) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .wrapContentSize(),
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

/** Preview **/

@Preview
@Composable
private fun ItemUserPreview() {
    ItemUser(user = mockUser)
}
