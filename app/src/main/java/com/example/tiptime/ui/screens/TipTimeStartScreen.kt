package com.example.tiptime.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiptime.R
import com.example.tiptime.ui.components.EditNumberField
import com.example.tiptime.ui.components.RoundTheTipRow
import com.example.tiptime.ui.viewmodels.TipViewModel

@Composable
fun TipTimeStartScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    tipViewModel: TipViewModel = viewModel(),
) {
    val tipUiState by tipViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = tipUiState.amountInput,
            onValueChanged = { tipViewModel.updateAmountInput(it) },
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipUiState.tipInput,
            onValueChanged = { tipViewModel.updateTipInput(it) },
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth(),
        )
        RoundTheTipRow(
            roundUp = tipUiState.roundUp,
            onRoundUpChanged = { tipViewModel.updateRoundUp(it) },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount, tipUiState.tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = onNextButtonClicked,
        ) {
            Text(stringResource(R.string.next))
        }
        Spacer(modifier = Modifier.height(150.dp))
    }
}