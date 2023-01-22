/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yourcompany.android.quizme.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yourcompany.android.quizme.R
import com.yourcompany.android.quizme.business.QuizViewModel

@Composable
fun QuizScreen(contentPadding: PaddingValues, quizViewModel: QuizViewModel) {
  Column(
    modifier = Modifier
      .padding(contentPadding)
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    var checked by remember { mutableStateOf(false) }
    val onCheckedChange: (Boolean) -> Unit = { value -> checked = value }

    val questions by quizViewModel.questions.observeAsState()
    val answers = remember { mutableMapOf<String, String>() }
    val onAnswerChanged: (String, String) -> Unit = { question, answer -> answers[question] = answer }

    ScreenIntroTexts()

    QuizInputFields(questions, onAnswerChanged)

    CheckBox(
      isChecked = checked,
      onChange = onCheckedChange
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      ImageDecoration(modifier = Modifier.weight(1f))
      if (checked) {
        quizViewModel.fetchExtendedQuestions()
        SubmitButton(checked, stringResource(id = R.string.try_me)) {
          quizViewModel.verifyAnswers(answers)
        }
      }
    }
  }
  DisposableEffect(quizViewModel) {
    quizViewModel.onAppear()
    onDispose { quizViewModel.onDisappear() }
  }
}

@Composable
fun ScreenIntroTexts() {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = stringResource(R.string.welcome),
      modifier = Modifier.padding(4.dp),
      style = MaterialTheme.typography.h4
    )
    Text(
      text = stringResource(R.string.quiz_subtitle),
      style = MaterialTheme.typography.h6
    )
  }
}

@Composable
fun QuizInputFields(questions: List<String>?, onAnswerChanged: (String, String) -> Unit) {
  Column {
    questions?.forEach { question ->
      key(question){
        QuizInput(question = question, onAnswerChanged = onAnswerChanged)
      }
    }
  }
}

@Composable
fun QuizInput(question: String, onAnswerChanged: (String, String) -> Unit) {
  Log.d(MAIN, "QuizInput $question")
  var input by remember { mutableStateOf("") }

  TextField(
    value = input,
    onValueChange = { value ->
      run {
        input = value
        onAnswerChanged(question, input)
      }
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 16.dp),
    placeholder = {
      Text(text = question)
    }
  )
}

@Composable
fun CheckBox(
  isChecked: Boolean,
  onChange: (Boolean) -> Unit
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Log.d(MAIN, "Checked state $isChecked")
    Checkbox(
      checked = isChecked,
      onCheckedChange = onChange
    )
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      text = stringResource(id = R.string.having_fun)
    )
  }
}

@Composable
fun SubmitButton(isChecked: Boolean, text: String, onClick: () -> Unit) {
  Log.d(MAIN, "Button recomposed")
  ExtendedFloatingActionButton(
    text = {
      Text(text = text)
    },
    shape = RectangleShape,
    onClick = onClick,
    backgroundColor = if (isChecked) MaterialTheme.colors.secondary else Color.Gray,
    modifier = Modifier
      .fillMaxWidth()
  )
}

@Composable
fun ImageDecoration(modifier: Modifier) {
  Image(
    painter = painterResource(id = R.drawable.droid_reading),
    contentDescription = "droid reading questions",
    modifier = modifier
  )
}
