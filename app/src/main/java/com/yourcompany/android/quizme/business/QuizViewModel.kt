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

package com.yourcompany.android.quizme.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.android.quizme.repository.QuestionsRepository
import com.yourcompany.android.quizme.ui.screens.MAIN
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
  private val repository = QuestionsRepository()

  private val _state = MutableLiveData<ScreenState>(ScreenState.Quiz)
  val state: LiveData<ScreenState> = _state

  private val _event = MutableSharedFlow<Event?>()
  val event: SharedFlow<Event?> = _event

  private val _questions = MutableLiveData<List<String>>()
  val questions: LiveData<List<String>> = _questions

  init {
    fetchQuestions()
  }

  fun onAppear() {
    Log.d(MAIN, "QuizScreen appears")
  }

  fun onDisappear() {
    Log.d(MAIN, "QuizScreen disappears")
    fetchQuestions()
  }

  private fun fetchQuestions() {
    _questions.value =  repository.getQuestions()
  }

  fun fetchExtendedQuestions() {
    _questions.value = repository.getExtendedQuestions()
  }

  fun verifyAnswers(answers: MutableMap<String, String>) {
    viewModelScope.launch {
      _event.emit(Event.Loading)
      delay(1000)

      val result = repository.verifyAnswers(answers)
      when (result) {
        is Event.Error -> _event.emit(result)
        else -> _state.value = ScreenState.Success
      }
    }
  }

  fun startAgain() {
    _state.value = ScreenState.Quiz
    viewModelScope.launch {
      _event.emit(null)
    }
  }
}

sealed class ScreenState {
  object Success : ScreenState()
  object Quiz : ScreenState()
}

sealed class Event {
  data class Error(val message: String) : Event()
  object Loading : Event()
}

