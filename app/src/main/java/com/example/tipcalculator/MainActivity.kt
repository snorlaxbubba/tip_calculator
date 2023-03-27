package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf("") }
    var randomGenerator by remember { mutableStateOf("") }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tipPercentageAmount = tipPercentage.toDoubleOrNull() ?: 0.0
    val randomGeneratorAmount = randomGenerator.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercentageAmount)
    val serviceCost = calculateServiceCost(amount, tipPercentageAmount)
    val rand = Random()
    val randomDouble: Double = rand.nextDouble() * 1999 + 1
    val randomTip: Double = rand.nextDouble() * 50 + 1
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(onClick = { tipPercentage = String.format("%.2f", randomTip) }) {
            Text("Generate Random Tip")

        }
        Button(onClick = { serviceCostAmountInput = String.format("%.2f", randomDouble) }) {
            Text("Generate Random Service Cost")
        }
        Row() {

            Button(onClick = { tipPercentage = "10.0" }) {
                Text("10%")
            }
            Button(onClick = { tipPercentage = "15.0" }) {
                Text("15%")
            }
            Button(onClick = { tipPercentage = "20.0" }) {
                Text("20%")
            }
            Button(onClick = { tipPercentage = "25.0" }) {
                Text("25%")
            }
        }
        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(24.dp))
        Text (
            text = "Tip Percentage $tipPercentageAmount",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.service_amount, NumberFormat.getCurrencyInstance().format(amount)),
        modifier = Modifier.align(Alignment.CenterHorizontally),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.total_amount, serviceCost),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )


    }
}
private fun calculateTip(
    amount: Double,
    tipPercent: Double
): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

private fun calculateServiceCost(
    amount: Double,
    tipPercent: Double
): String {
    val serviceCost = amount + tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(serviceCost)
}
@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {

    var serviceCostAmountInput by remember { mutableStateOf("") }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
        TextField(
            value = value,
            onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}