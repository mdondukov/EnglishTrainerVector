package team.fightcats.englishtrainervector.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class Result(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "game_score")
    val gameScore: Int,
    @ColumnInfo(name = "correct_answers")
    val correctAnswers: Int,
    @ColumnInfo(name = "total_questions")
    val totalQuestions: Int
)