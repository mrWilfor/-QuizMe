/*
 * Copyright (c) 2022 Razeware LLC
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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourcompany.android.quizme.business.Event
import com.yourcompany.android.quizme.business.QuizViewModel
import com.yourcompany.android.quizme.business.ScreenState
import com.yourcompany.android.quizme.ui.theme.QuizMeTheme
import com.yourcompany.android.quizme.ui.widgets.ErrorDialog
import com.yourcompany.android.quizme.ui.widgets.LoadingIndicator

const val MAIN = "MainLog"

@Composable
fun MainScreen(contentPadding: PaddingValues, quizViewModel: QuizViewModel) {
  // 1
  val state by quizViewModel.state.observeAsState()
  val event by quizViewModel.event.collectAsState(null)

  // 2
  when (state) {
    is ScreenState.Quiz -> {
      QuizScreen(
        contentPadding = contentPadding,
        quizViewModel = quizViewModel
      )
      // 3
      when (val e = event) {
        is Event.Error -> {
          ErrorDialog(message = e.message)
        }
        is Event.Loading -> {
          LoadingIndicator()
        }
        else -> {}
      }
    }
    is ScreenState.Success -> {
      ResultScreen(
        contentPadding = contentPadding,
        quizViewModel = quizViewModel
      )
    }
    else -> {}
  }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  QuizMeTheme {
    MainScreen(PaddingValues(8.dp), QuizViewModel())
  }
}