package com.example.tictactoeshira

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val board = Array(3) { Array(3) { "" } }
    private var currentPlayer = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // ודא שקובץ זה קיים ב-res/layout

        val buttons = try {
            arrayOf(
                arrayOf(
                    findViewById<Button>(R.id.button1),
                    findViewById<Button>(R.id.button2),
                    findViewById<Button>(R.id.button3)
                ),
                arrayOf(
                    findViewById<Button>(R.id.button4),
                    findViewById<Button>(R.id.button5),
                    findViewById<Button>(R.id.button6)
                ),
                arrayOf(
                    findViewById<Button>(R.id.button7),
                    findViewById<Button>(R.id.button8),
                    findViewById<Button>(R.id.button9)
                )
            )
        } catch (e: Exception) {
            Log.e("MainActivity", "Error finding buttons: ${e.message}")
            null
        }

        if (buttons == null) {
            Toast.makeText(this, "Error loading buttons. Please check your layout.", Toast.LENGTH_LONG).show()
            return
        }

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = currentPlayer
                        buttons[i][j].text = currentPlayer
                        if (checkWin()) {
                            Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT).show()
                            resetBoard(buttons)
                        } else if (isBoardFull()) {
                            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
                            resetBoard(buttons)
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                        }
                    }
                }
            }
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun resetBoard(buttons: Array<Array<Button>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ""
                buttons[i][j].text = ""
            }
        }
        currentPlayer = "X"
    }
}