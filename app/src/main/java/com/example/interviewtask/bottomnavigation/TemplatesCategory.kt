package com.example.interviewtask.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.interviewtask.TemplateViewModel
import com.example.interviewtask.data.remote.TemplateItems

@Composable
fun TemplatesCategoryScreen(viewModel: TemplateViewModel,navController: NavController){
    val data = viewModel.templates
    val selectedCategory = viewModel.selectedCategory

    if (data == null || selectedCategory == null){
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else{

        val category = data.keys.toList()

        val selectedTemplate = data[selectedCategory]?.templates ?: emptyList()

        CategoryButtons(category,selectedCategory) {viewModel.onCategorySelected(it) }
        SelectedCategoryContent(selectedTemplate,navController)

    }
}



@Composable
fun CategoryButtons(categories: List<String>,
                    selectedCategory: String,
                    onCategoryClick :(String) -> Unit  )
{

    LazyRow() {

         items(categories){ category ->

             val isSelected = category == selectedCategory

            Button(onClick = {onCategoryClick(category) }, colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color.Black else Color.LightGray,
                contentColor = if (isSelected) Color.White else Color.Black
            ), modifier = Modifier.padding(start = 4.dp)) {
                Text(category, fontSize = 13.sp)
            }
        }

    }
}

@Composable
fun SelectedCategoryContent(templates: List<TemplateItems>,navController: NavController){

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {

        items(templates){ item ->

            Card(modifier = Modifier.padding(8.dp).wrapContentHeight().wrapContentWidth(),

                onClick = {navController.navigate(Screens.ImageSelection.passData(item.url))}) {

                AsyncImage(item.url, contentDescription = "templates")
            }

        }
    }

}