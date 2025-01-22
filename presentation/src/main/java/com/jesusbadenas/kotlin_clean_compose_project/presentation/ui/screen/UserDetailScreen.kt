package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.LoadingView
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDetailScreen(
    user: User
) {
    val viewModel: UserDetailsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    // TODO: add user detail
    LoadingView()
}

/*
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="viewModel"
            type="com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:id="@+id/user_detail_view"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:orientation="vertical"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <ImageView
                android:id="@+id/iv_cover"
                android:layout_width="match_parent"
                android:layout_height="@dimen/iv_cover_height"
                android:contentDescription="@string/user_image_description"
                app:imageUrl="@{viewModel.user.imageUrl}" />

            <TextView
                android:id="@+id/tv_fullname"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="@dimen/header_margin"
                android:freezesText="true"
                android:gravity="center"
                android:text="@{viewModel.user.name}"
                android:textSize="@dimen/header_text_size"
                android:textStyle="bold" />

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginLeft="@dimen/activity_horizontal_margin"
                android:layout_marginRight="@dimen/activity_horizontal_margin"
                android:orientation="vertical">

                <TextView
                    style="@style/TextViewField"
                    android:text="@string/view_text_email" />

                <TextView
                    android:id="@+id/tv_email"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:freezesText="true"
                    android:text="@{viewModel.user.email}" />

                <TextView
                    style="@style/TextViewField"
                    android:text="@string/view_text_website" />

                <TextView
                    android:id="@+id/tv_description"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:freezesText="true"
                    android:text="@{viewModel.user.website}" />
            </LinearLayout>
        </LinearLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
 */
