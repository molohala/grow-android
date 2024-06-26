package com.molohala.grow.ui.main.profile.setting.profileedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bestswlkh0310.mydesignsystem.component.button.MyCTAButton
import com.bestswlkh0310.mydesignsystem.component.button.MyRadioButton
import com.bestswlkh0310.mydesignsystem.component.dialog.MyDialog
import com.bestswlkh0310.mydesignsystem.R
import com.bestswlkh0310.mydesignsystem.component.textfield.MyTextField
import com.bestswlkh0310.mydesignsystem.component.topappbar.MyTopAppBar
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.specific.text.Headline
import com.molohala.grow.ui.root.AppViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileEditScreen(
    navController: NavController,
    viewModel: ProfileEditViewModel = viewModel(),
    appViewModel: AppViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiAppState by appViewModel.uiState.collectAsState()
    var showFetchFailureDialog by remember { mutableStateOf(false) }
    var showPatchFailureDialog by remember { mutableStateOf(false) }
    var showPatchSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        (uiAppState.profile as? FetchFlow.Success)?.data?.let {
            viewModel.updateBio(it.bio)
            viewModel.updateJob(it.job)
        }
        viewModel.fetchLanguages()
        viewModel.fetchMyLanguages()
        viewModel.fetchJobs()
        viewModel.uiSideEffect.collect {
            when (it) {
                ProfileEditSideEffect.FetchFailure -> {
                    showFetchFailureDialog = true
                }

                ProfileEditSideEffect.PatchFailure -> {
                    showPatchFailureDialog = true
                }

                ProfileEditSideEffect.PatchSuccess -> {
                    showPatchSuccessDialog = true
                    appViewModel.fetchProfile()
                }
            }
        }
    }

    MyTopAppBar(
        text = "프로필 설정",
        onClickBackButton = {
            navController.popBackStack()
        },
        backgroundColor = MyTheme.colorScheme.backgroundAlt
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Headline(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = "소개글"
                        )
                        MyTextField(
                            modifier = Modifier
                                .heightIn(52.dp, 100.dp),
                            value = uiState.bio,
                            onValueChange = viewModel::updateBio,
                            hint = "",
                            singleLine = false
                        )
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Headline(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = "직군"
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.jobs.let { jobs ->
                                when (jobs) {
                                    is FetchFlow.Failure -> {}
                                    is FetchFlow.Fetching -> {}
                                    is FetchFlow.Success -> {
                                        jobs.data.forEach { job ->
                                            MyRadioButton(
                                                text = job.ifEmpty { "Developer" },
                                                isSelected = job == uiState.selectedJob,
                                                onClick = {
                                                    viewModel.updateJob(job)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Headline(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = "사용 언어"
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.languages.let { langs ->
                                when (langs) {
                                    is FetchFlow.Failure -> {}
                                    is FetchFlow.Fetching -> {}
                                    is FetchFlow.Success -> {
                                        val myLanguage =
                                            (uiState.myLanguages as? FetchFlow.Success)?.data
                                                ?: return@let
                                        langs.data.forEach { lang ->
                                            MyRadioButton(
                                                text = lang.name, selectedIcon = R.drawable.ic_check,
                                                unselectedIcon = R.drawable.ic_add_line,
                                                isSelected = myLanguage.contains(lang)
                                            ) {
                                                viewModel.updateMyLanguages(lang)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(92.dp))
                }
            }
            MyCTAButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                leftIcon = R.drawable.ic_check,
                text = "완료",
            ) {
                viewModel.completeSetting()
            }
        }
    }

    if (showPatchSuccessDialog) {
        MyDialog(
            title = "프로필 정보 수정 성공",
            onDismissRequest = {
                showPatchSuccessDialog = false
            }
        )
    }

    if (showFetchFailureDialog) {
        MyDialog(
            title = "프로필 정보 불러오기 실패",
            onDismissRequest = {
                showFetchFailureDialog = false
                navController.popBackStack()
            }
        )
    }

    if (showPatchFailureDialog) {
        MyDialog(
            title = "프로필 정보 수정 실패",
            onDismissRequest = {
                showPatchFailureDialog = false
            }
        )
    }
}