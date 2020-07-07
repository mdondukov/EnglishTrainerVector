package team.fightcats.englishtrainervector.data

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import team.fightcats.englishtrainervector.model.Question
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

class QuestionXmlPullParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Question> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Question> {
        val questions = mutableListOf<Question>()
        parser.require(XmlPullParser.START_TAG, ns, "questions")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) continue
            if (parser.name == "question") questions.add(readQuestion(parser))
        }
        return questions
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readQuestion(parser: XmlPullParser): Question {
        parser.require(XmlPullParser.START_TAG, ns, "question")
        var word: String? = null
        var translation: String? = null
        var variants: List<String>? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) continue
            when (parser.name) {
                "word" -> word = readWord(parser)
                "translation" -> translation = readTranslation(parser)
                "variants" -> variants = readVariants(parser)
            }
        }

        return Question(word, translation, variants)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readWord(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "word")
        val word = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "word")
        return word
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readTranslation(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "translation")
        val translation = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "translation")
        return translation
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readVariants(parser: XmlPullParser): List<String>? {
        val variants = mutableListOf<String>()
        parser.require(XmlPullParser.START_TAG, ns, "variants")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) continue
            if (parser.name == "variant") variants.add(readVariant(parser))
        }
        return variants
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readVariant(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "variant")
        val variant = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "variant")
        return variant
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }
}
