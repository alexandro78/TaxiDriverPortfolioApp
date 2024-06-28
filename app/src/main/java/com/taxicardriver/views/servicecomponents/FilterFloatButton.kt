package com.taxicardriver.views.servicecomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.cabdriver.R

@Composable
fun FilterFloatButton() {
    ConstraintLayout(funConstrainSetFilterFloatButton(), modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("filterFloatButton"),
            horizontalAlignment = Alignment.Start
        ) {
            FloatingActionButton(modifier = Modifier
                .size(55.dp)
                .offset(x = 25.dp),
                shape = CircleShape,
                containerColor = Color(0xFF8C92AC),
                onClick = { }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon_),
                    contentDescription = "Add",
                    modifier = Modifier
                        .size(26.dp)
                        .offset(y = 3.dp),
                    tint = Color(0xFFFFFDD0)
                )
            }
        }
    }
}

private fun funConstrainSetFilterFloatButton(): ConstraintSet {
    return ConstraintSet {
        val filterFloatButton = createRefFor("filterFloatButton")
        val horizontalGuideline = createGuidelineFromTop(0.7f)

        constrain(filterFloatButton) {
            top.linkTo(horizontalGuideline)
        }
    }
}

