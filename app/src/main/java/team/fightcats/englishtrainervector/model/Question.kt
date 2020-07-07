package team.fightcats.englishtrainervector.model

data class Question(
    val word: String?,
    val translation: String?,
    val variants: List<String>?
)