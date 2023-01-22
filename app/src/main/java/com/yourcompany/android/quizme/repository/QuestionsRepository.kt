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

package com.yourcompany.android.quizme.repository

import com.yourcompany.android.quizme.business.Event

class QuestionsRepository {
  private val quiz = listOf(
    Question(
      "What's the best programming language?",
      "kotlin",
      "Are you sure it's the best language?"
    ),
    Question(
      "What's the best OS?",
      "android",
      "This is not the best OS"
    ),
    Question(
      "The answer to Life, The Universe and Everything?",
      "42",
      "You should think better about the ultimate question of life!"
    )
  )

  fun getQuestions(): List<String> {
    return quiz.take(2).map { it.question }
  }

  fun getExtendedQuestions(): List<String> {
    return quiz.take(3).map { it.question }
  }

  fun verifyAnswers(answers: Map<String, String>): Event? {
    if (answers.size < quiz.size) {
      return Event.Error("Please complete the quiz!")
    }
    quiz.forEach { item ->
      val answer = answers[item.question]?.lowercase()?.trim()
      if (answer != item.answer) {
        return Event.Error(item.message)
      }
    }
    return null
  }
}

data class Question(
  val question: String,
  val answer: String,
  val message: String
)